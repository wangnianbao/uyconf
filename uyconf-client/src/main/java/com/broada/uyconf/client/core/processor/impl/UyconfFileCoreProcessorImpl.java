package com.broada.uyconf.client.core.processor.impl;

import java.util.HashMap;
import java.util.Map;

import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.common.model.UyconfCenterFile;
import com.broada.uyconf.client.common.update.IUyconfUpdatePipeline;
import com.broada.uyconf.client.config.UyClientConfig;
import com.broada.uyconf.client.core.processor.UyconfCoreProcessor;
import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.client.store.UyconfStoreProcessor;
import com.broada.uyconf.client.store.UyconfStoreProcessorFactory;
import com.broada.uyconf.client.store.inner.UyconfCenterStore;
import com.broada.uyconf.client.store.processor.model.UyconfValue;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.client.watch.WatchMgr;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.core.filetype.FileTypeProcessorUtils;
import com.broada.uyconf.core.common.utils.GsonUtils;

/**
 * 配置文件处理器实现
 *
 * @author wnb
 *
 */
public class UyconfFileCoreProcessorImpl implements UyconfCoreProcessor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfFileCoreProcessorImpl.class);

    // 监控器
    private WatchMgr watchMgr = null;

    // 抓取器
    private FetcherMgr fetcherMgr = null;

    // 仓库算子
    private UyconfStoreProcessor uyconfStoreProcessor = UyconfStoreProcessorFactory.getUyconfStoreFileProcessor();

    // bean registry
    private Registry registry = null;

    public UyconfFileCoreProcessorImpl(WatchMgr watchMgr, FetcherMgr fetcherMgr, Registry registry) {

        this.fetcherMgr = fetcherMgr;
        this.watchMgr = watchMgr;
        this.registry = registry;
    }

    /**
     *
     */
    @Override
    public void processAllItems() {

        /**
         * 配置文件列表处理
         */
        for (String fileName : uyconfStoreProcessor.getConfKeySet()) {

            processOneItem(fileName);
        }
    }

    @Override
    public void processOneItem(String key) {

        LOGGER.debug("==============\tstart to process uyconf file: " + key +
                "\t=============================");

        UyconfCenterFile uyconfCenterFile = (UyconfCenterFile) uyconfStoreProcessor.getConfData(key);

        try {
            updateOneConfFile(key, uyconfCenterFile);
        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
        }
    }

    /**
     * 更新 一個配置文件, 下载、注入到仓库、Watch 三步骤
     */
    private void updateOneConfFile(String fileName, UyconfCenterFile uyconfCenterFile) throws Exception {

        if (uyconfCenterFile == null) {
            throw new Exception("cannot find uyconfCenterFile " + fileName);
        }

        String filePath = fileName;
        Map<String, Object> dataMap = new HashMap<String, Object>();

        //
        // 开启uyconf才需要远程下载, 否则就本地就好
        //
        if (UyClientConfig.getInstance().ENABLE_UYCONF) {

            //
            // 下载配置
            //
            try {

                String url = uyconfCenterFile.getRemoteServerUrl();
                filePath = fetcherMgr.downloadFileFromServer(url, fileName, uyconfCenterFile.getFileDir());

            } catch (Exception e) {

                //
                // 下载失败了, 尝试使用本地的配置
                //

                LOGGER.error(e.toString(), e);
                LOGGER.warn("using local properties in class path: " + fileName);

                // change file path
                filePath = fileName;
            }
            LOGGER.debug("download ok.");
        }

        try {
            dataMap = FileTypeProcessorUtils.getKvMap(uyconfCenterFile.getSupportFileTypeEnum(),
                    uyconfCenterFile.getFilePath());
        } catch (Exception e) {
            LOGGER.error("cannot get kv data for " + filePath, e);
        }

        //
        // 注入到仓库中
        //
        uyconfStoreProcessor.inject2Store(fileName, new UyconfValue(null, dataMap));
        LOGGER.debug("inject ok.");

        //
        // 开启uyconf才需要进行watch
        //
        if (UyClientConfig.getInstance().ENABLE_UYCONF) {
            //
            // Watch
            //
            UyConfCommonModel uyConfCommonModel = uyconfStoreProcessor.getCommonModel(fileName);
            if (watchMgr != null) {
                watchMgr.watchPath(this, uyConfCommonModel, fileName, UyConfigTypeEnum.FILE,
                        GsonUtils.toJson(uyconfCenterFile.getKV()));
                LOGGER.debug("watch ok.");
            } else {
                LOGGER.warn("cannot monitor {} because watch mgr is null", fileName);
            }
        }
    }

    /**
     * 更新消息: 某个配置文件 + 回调
     */
    @Override
    public void updateOneConfAndCallback(String key) throws Exception {

        // 更新 配置
        updateOneConf(key);

        // 回调
        UyconfCoreProcessUtils.callOneConf(uyconfStoreProcessor, key);
        callUpdatePipeline(key);
    }

    /**
     * @param key
     */
    private void callUpdatePipeline(String key) {

        Object object = uyconfStoreProcessor.getConfData(key);
        if (object != null) {
            UyconfCenterFile uyconfCenterFile = (UyconfCenterFile) object;

            IUyconfUpdatePipeline iUyconfUpdatePipeline =
                    UyconfCenterStore.getInstance().getiUyconfUpdatePipeline();
            if (iUyconfUpdatePipeline != null) {
                try {
                    iUyconfUpdatePipeline.reloadUyconfFile(key, uyconfCenterFile.getFilePath());
                } catch (Exception e) {
                    LOGGER.error(e.toString(), e);
                }
            }
        }
    }

    /**
     * 更新消息：某个配置文件
     */
    private void updateOneConf(String fileName) throws Exception {

        UyconfCenterFile uyconfCenterFile = (UyconfCenterFile) uyconfStoreProcessor.getConfData(fileName);

        if (uyconfCenterFile != null) {

            // 更新仓库
            updateOneConfFile(fileName, uyconfCenterFile);

            // 更新实例
            inject2OneConf(fileName, uyconfCenterFile);
        }
    }

    /**
     * 为某个配置文件进行注入实例中
     */
    private void inject2OneConf(String fileName, UyconfCenterFile uyconfCenterFile) {

        if (uyconfCenterFile == null) {
            return;
        }

        try {

            //
            // 获取实例
            //

            Object object;
            try {

                object = uyconfCenterFile.getObject();
                if (object == null) {
                    object = registry.getFirstByType(uyconfCenterFile.getCls(), false, true);
                }

            } catch (Exception e) {

                LOGGER.error(e.toString());
                object = null;
            }

            // 注入实体中
            uyconfStoreProcessor.inject2Instance(object, fileName);

        } catch (Exception e) {
            LOGGER.warn(e.toString(), e);
        }
    }

    @Override
    public void inject2Conf() {

        /**
         * 配置文件列表处理
         */
        for (String key : uyconfStoreProcessor.getConfKeySet()) {

            LOGGER.debug("==============\tstart to inject value to uyconf file item instance: " + key +
                    "\t=============================");

            UyconfCenterFile uyconfCenterFile = (UyconfCenterFile) uyconfStoreProcessor.getConfData(key);

            inject2OneConf(key, uyconfCenterFile);
        }
    }
}
