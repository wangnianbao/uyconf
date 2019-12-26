package com.broada.uyconf.client.scan.inner.dynamic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.broada.uyconf.client.common.annotations.UyconfFile;
import com.broada.uyconf.client.common.annotations.UyconfUpdateService;
import com.broada.uyconf.client.common.model.UyconfKey;
import com.broada.uyconf.client.common.update.IUyconfUpdate;
import com.broada.uyconf.client.common.update.IUyconfUpdatePipeline;
import com.broada.uyconf.client.config.UyClientConfig;
import com.broada.uyconf.client.scan.inner.common.ScanVerify;
import com.broada.uyconf.client.scan.inner.dynamic.model.ScanDynamicModel;
import com.broada.uyconf.client.scan.inner.statically.model.ScanStaticModel;
import com.broada.uyconf.client.store.UyconfStorePipelineProcessor;
import com.broada.uyconf.client.store.UyconfStoreProcessor;
import com.broada.uyconf.client.store.UyconfStoreProcessorFactory;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 动态扫描 与 Store模块的转换器
 *
 * @author wnb
 * 14-6-18
 */
public class ScanDynamicStoreAdapter {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ScanDynamicStoreAdapter.class);

    /**
     * 扫描更新回调函数
     */
    public static void scanUpdateCallbacks(ScanStaticModel scanModel, Registry registry) {

        // 扫描出来
        ScanDynamicModel scanDynamicModel = analysis4UyconfUpdate(scanModel, registry);

        // 写到仓库中
        transformUpdateService(scanDynamicModel.getUyconfUpdateServiceInverseIndexMap());
        transformPipelineService(scanDynamicModel.getUyconfUpdatePipeline());
    }

    /**
     * 第二次扫描, 获取更新 回调的实例<br/>
     * <p/>
     * 分析出更新操作的相关配置文件内容
     */
    private static ScanDynamicModel analysis4UyconfUpdate(ScanStaticModel scanModel, Registry registry) {

        // 配置项或文件
        Map<UyconfKey, List<IUyconfUpdate>> inverseMap = new HashMap<UyconfKey, List<IUyconfUpdate>>();

        Set<Class<?>> uyconfUpdateServiceSet = scanModel.getUyconfUpdateService();
        for (Class<?> uyconfUpdateServiceClass : uyconfUpdateServiceSet) {

            // 回调对应的参数
            UyconfUpdateService uyconfUpdateService =
                    uyconfUpdateServiceClass.getAnnotation(UyconfUpdateService.class);

            //
            // 校验是否有继承正确,是否继承IUyconfUpdate
            if (!ScanVerify.hasIUyconfUpdate(uyconfUpdateServiceClass)) {
                continue;
            }

            //
            // 获取回调接口实例
            IUyconfUpdate iUyconfUpdate = getIUyconfUpdateInstance(uyconfUpdateServiceClass, registry);
            if (iUyconfUpdate == null) {
                continue;
            }

            //
            // 配置项
            processItems(inverseMap, uyconfUpdateService, iUyconfUpdate);

            //
            // 配置文件
            processFiles(inverseMap, uyconfUpdateService, iUyconfUpdate);

        }

        // set data
        ScanDynamicModel scanDynamicModel = new ScanDynamicModel();
        scanDynamicModel.setUyconfUpdateServiceInverseIndexMap(inverseMap);

        //
        // set update pipeline
        //
        if (scanModel.getiUyconfUpdatePipeline() != null) {
            IUyconfUpdatePipeline iUyconfUpdatePipeline = getIUyconfUpdatePipelineInstance(scanModel
                    .getiUyconfUpdatePipeline(), registry);
            if (iUyconfUpdatePipeline != null) {
                scanDynamicModel.setUyconfUpdatePipeline(iUyconfUpdatePipeline);
            }
        }

        return scanDynamicModel;
    }

    /**
     * 获取回调对应配置项列表
     */
    private static void processItems(Map<UyconfKey, List<IUyconfUpdate>> inverseMap,
                                     UyconfUpdateService uyconfUpdateService, IUyconfUpdate iUyconfUpdate) {

        List<String> itemKeys = Arrays.asList(uyconfUpdateService.itemKeys());

        // 反索引
        for (String key : itemKeys) {

            UyconfKey uyconfKey = new UyconfKey(UyConfigTypeEnum.ITEM, key);
            addOne2InverseMap(uyconfKey, inverseMap, iUyconfUpdate);
        }
    }

    /**
     * 获取回调对应的配置文件列表
     */
    private static void processFiles(Map<UyconfKey, List<IUyconfUpdate>> inverseMap,
                                     UyconfUpdateService uyconfUpdateService, IUyconfUpdate iUyconfUpdate) {

        // 反索引
        List<Class<?>> classes = Arrays.asList(uyconfUpdateService.classes());
        for (Class<?> curClass : classes) {

            // 获取其注解
            UyconfFile uyconfFile = curClass.getAnnotation(UyconfFile.class);
            if (uyconfFile == null) {

                LOGGER.error("cannot find UyconfFile annotation for class when set callback: {} ",
                        curClass.toString());
                continue;
            }

            UyconfKey uyconfKey = new UyconfKey(UyConfigTypeEnum.FILE, uyconfFile.filename());
            addOne2InverseMap(uyconfKey, inverseMap, iUyconfUpdate);
        }

        // 反索引
        List<String> fileKeyList = Arrays.asList(uyconfUpdateService.confFileKeys());
        for (String fileKey : fileKeyList) {

            UyconfKey uyconfKey = new UyconfKey(UyConfigTypeEnum.FILE, fileKey);
            addOne2InverseMap(uyconfKey, inverseMap, iUyconfUpdate);
        }

    }

    /**
     * 获取回调接口的实现
     * <p/>
     * // 回调函数需要实例化出来,这里
     * // 非Spring直接New
     * // Spring要GetBean
     * //
     */
    private static IUyconfUpdate getIUyconfUpdateInstance(Class<?> uyconfUpdateServiceClass, Registry registry) {

        Object iUyconfUpdate = registry.getFirstByType(uyconfUpdateServiceClass, true);
        if (iUyconfUpdate == null) {
            return null;
        }
        return (IUyconfUpdate) iUyconfUpdate;

    }

    /**
     * 获取回调接口的实现
     * //
     */
    private static IUyconfUpdatePipeline getIUyconfUpdatePipelineInstance(
            Class<IUyconfUpdatePipeline> uyconfUpdateServiceClass,
            Registry registry) {

        Object iUyconfUpdate = registry.getFirstByType(uyconfUpdateServiceClass, true);
        if (iUyconfUpdate == null) {
            return null;
        }
        return (IUyconfUpdatePipeline) iUyconfUpdate;

    }

    /**
     * 将一个配置回调item写到map里
     */
    private static void addOne2InverseMap(UyconfKey uyconfKey, Map<UyconfKey, List<IUyconfUpdate>> inverseMap,
                                          IUyconfUpdate iUyconfUpdate) {

        // 忽略的key 应该忽略掉
        if (UyClientConfig.getInstance().getIgnoreUyconfKeySet().contains(uyconfKey.getKey())) {
            return;
        }

        List<IUyconfUpdate> serviceList;

        if (inverseMap.containsKey(uyconfKey)) {
            inverseMap.get(uyconfKey).add(iUyconfUpdate);
        } else {
            serviceList = new ArrayList<IUyconfUpdate>();
            serviceList.add(iUyconfUpdate);
            inverseMap.put(uyconfKey, serviceList);
        }
    }

    /**
     * 第二次扫描<br/>
     * 转换 pipeline 回调函数，将其写到 仓库中
     */
    private static void transformPipelineService(IUyconfUpdatePipeline iUyconfUpdatePipeline) {

        UyconfStorePipelineProcessor uyconfStorePipelineProcessor = UyconfStoreProcessorFactory
                .getUyconfStorePipelineProcessor();
        uyconfStorePipelineProcessor.setUyconfUpdatePipeline(iUyconfUpdatePipeline);
    }

    /**
     * 第二次扫描<br/>
     * 转换 更新 回调函数，将其写到 仓库中
     */
    private static void transformUpdateService(Map<UyconfKey,
            List<IUyconfUpdate>> uyconfUpdateServiceInverseIndexMap) {

        UyconfStoreProcessor uyconfStoreProcessorFile = UyconfStoreProcessorFactory.getUyconfStoreFileProcessor();
        UyconfStoreProcessor uyconfStoreProcessorItem = UyconfStoreProcessorFactory.getUyconfStoreItemProcessor();

        for (UyconfKey uyconfKey : uyconfUpdateServiceInverseIndexMap.keySet()) {

            //
            //
            //

            try {
                if (uyconfKey.getUyConfigTypeEnum().equals(UyConfigTypeEnum.FILE)) {

                    if (!uyconfStoreProcessorFile.hasThisConf(uyconfKey.getKey())) {
                        throw new Exception();
                    }

                    uyconfStoreProcessorFile.addUpdateCallbackList(uyconfKey.getKey(),
                            uyconfUpdateServiceInverseIndexMap
                                    .get(uyconfKey));

                } else if (uyconfKey.getUyConfigTypeEnum().equals(UyConfigTypeEnum.ITEM)) {

                    if (!uyconfStoreProcessorItem.hasThisConf(uyconfKey.getKey())) {
                        throw new Exception();
                    }

                    uyconfStoreProcessorItem.addUpdateCallbackList(uyconfKey.getKey(),
                            uyconfUpdateServiceInverseIndexMap
                                    .get(uyconfKey));
                }

            } catch (Exception e) {
                // 找不到回调对应的配置，这是用户配置 错误了
                StringBuffer sb = new StringBuffer();
                sb.append("cannot find " + uyconfKey + "for: ");
                for (IUyconfUpdate serClass : uyconfUpdateServiceInverseIndexMap.get(uyconfKey)) {
                    sb.append(serClass.toString() + "\t");
                }
                LOGGER.error(sb.toString());
            }
        }
    }

}
