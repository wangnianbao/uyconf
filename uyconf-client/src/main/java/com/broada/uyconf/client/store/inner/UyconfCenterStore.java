package com.broada.uyconf.client.store.inner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.broada.uyconf.client.common.model.UyconfCenterItem;
import com.broada.uyconf.client.common.model.UyconfCenterBaseModel;
import com.broada.uyconf.client.common.model.UyconfCenterFile;
import com.broada.uyconf.client.common.update.IUyconfUpdatePipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置仓库,是个单例
 *
 * @author wnb
 * 14-6-9
 */
public class UyconfCenterStore {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfCenterStore.class);

    private UyconfCenterStore() {

    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例 没有绑定关系，而且只有被调用到时才会装载，从而实现了延迟加载。
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static UyconfCenterStore instance = new UyconfCenterStore();
    }

    public static UyconfCenterStore getInstance() {
        return SingletonHolder.instance;
    }

    // 每个配置文件一条
    // key: 配置文件名
    // value: 配置文件数据
    private Map<String, UyconfCenterFile> confFileMap = new HashMap<String, UyconfCenterFile>();

    // 每个配置Item一条
    // key: 配置项的Key
    // value: 配置项数据
    private Map<String, UyconfCenterItem> confItemMap = new HashMap<String, UyconfCenterItem>();

    // 主备切换时的Key列表
    private List<String> activeBackupKeyList;

    //
    private IUyconfUpdatePipeline iUyconfUpdatePipeline = null;

    // 标识本机器名
    private String machineName;

    /**
     * 存储 一个配置文件
     */
    public void storeOneFile(UyconfCenterBaseModel uyconfCenterBaseModel) {

        UyconfCenterFile uyconfCenterFile = (UyconfCenterFile) uyconfCenterBaseModel;

        String fileName = uyconfCenterFile.getFileName();

        if (confFileMap.containsKey(fileName)) {

            LOGGER.warn("There are two same fileName key!!!! " + fileName);
            UyconfCenterFile existCenterFile = confFileMap.get(fileName);

            // 如果是 同时使用了 注解式 和 非注解式 两种方式，则当修改时也要 进行 XML 式 reload
            if (uyconfCenterFile.isTaggedWithNonAnnotationFile()) {
                existCenterFile.setIsTaggedWithNonAnnotationFile(true);
            }

        } else {
            confFileMap.put(fileName, uyconfCenterFile);
        }
    }

    /**
     * 存储 一个配置项
     */
    public void storeOneItem(UyconfCenterBaseModel uyconfCenterBaseModel) {

        UyconfCenterItem uyconfCenterItem = (UyconfCenterItem) uyconfCenterBaseModel;

        String key = uyconfCenterItem.getKey();

        if (confItemMap.containsKey(key)) {

            LOGGER.error("There are two same item key!!!! " + "first: " + confItemMap.get(key).getClass().toString() +
                    ", Second: " + uyconfCenterItem.getClass().toString());
        } else {
            confItemMap.put(key, uyconfCenterItem);
        }
    }

    /**
     * 删除一个配置项
     */
    public void excludeOneItem(String key) {

        if (confItemMap.containsKey(key)) {
            confItemMap.remove(key);
        }
    }

    /**
     * 删除一个配置文件
     */
    public void excludeOneFile(String key) {

        if (confFileMap.containsKey(key)) {
            confFileMap.remove(key);
        }
    }

    public Map<String, UyconfCenterFile> getConfFileMap() {
        return confFileMap;
    }

    public Map<String, UyconfCenterItem> getConfItemMap() {
        return confItemMap;
    }

    public List<String> getActiveBackupKeyList() {
        return activeBackupKeyList;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public IUyconfUpdatePipeline getiUyconfUpdatePipeline() {
        return iUyconfUpdatePipeline;
    }

    public void setiUyconfUpdatePipeline(
            IUyconfUpdatePipeline iUyconfUpdatePipeline) {
        this.iUyconfUpdatePipeline = iUyconfUpdatePipeline;
    }
}
