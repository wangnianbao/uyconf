package com.broada.uyconf.client.core.processor.impl;

import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.common.model.UyconfCenterItem;
import com.broada.uyconf.client.common.update.IUyconfUpdatePipeline;
import com.broada.uyconf.client.config.UyClientConfig;
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

import com.broada.uyconf.client.core.processor.UyconfCoreProcessor;

/**
 * 配置项处理器实现
 *
 * @author wnb
 * 14-8-4
 */
public class UyconfItemCoreProcessorImpl implements UyconfCoreProcessor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfItemCoreProcessorImpl.class);

    // 监控器
    private WatchMgr watchMgr = null;

    // 抓取器
    private FetcherMgr fetcherMgr = null;

    // Registry
    private Registry registry = null;

    // 仓库算子
    private UyconfStoreProcessor uyconfStoreProcessor = UyconfStoreProcessorFactory.getUyconfStoreItemProcessor();

    public UyconfItemCoreProcessorImpl(WatchMgr watchMgr, FetcherMgr fetcherMgr, Registry registry) {
        this.registry = registry;
        this.fetcherMgr = fetcherMgr;
        this.watchMgr = watchMgr;
    }

    /**
     *
     */
    @Override
    public void processAllItems() {

        /**
         * 配置ITEM列表处理
         */
        for (String key : uyconfStoreProcessor.getConfKeySet()) {
            processOneItem(key);
        }
    }

    @Override
    public void processOneItem(String key) {
        LOGGER.debug("==============\tstart to process uyconf item: " + key + "\t=============================");

        UyconfCenterItem uyconfCenterItem = (UyconfCenterItem) uyconfStoreProcessor.getConfData(key);
        if (uyconfCenterItem != null) {
            try {
                updateOneConfItem(key, uyconfCenterItem);
            } catch (Exception e) {
                LOGGER.error(e.toString(), e);
            }
        }
    }

    /**
     * 更新 一个配置
     */
    private void updateOneConf(String keyName) throws Exception {

        UyconfCenterItem uyconfCenterItem = (UyconfCenterItem) uyconfStoreProcessor.getConfData(keyName);
        if (uyconfCenterItem != null) {

            // 更新仓库
            updateOneConfItem(keyName, uyconfCenterItem);

            // 更新实例
            inject2OneConf(keyName, uyconfCenterItem);
        }
    }

    /**
     * 更新一个配置
     */
    private void updateOneConfItem(String keyName, UyconfCenterItem uyconfCenterItem) throws Exception {

        if (uyconfCenterItem == null) {
            throw new Exception("cannot find uyconfCenterItem " + keyName);
        }

        String value = null;

        //
        // 开启uyconf才需要远程下载, 否则就用默认值
        //
        if (UyClientConfig.getInstance().ENABLE_UYCONF) {
            //
            // 下载配置
            //
            try {
                String url = uyconfCenterItem.getRemoteServerUrl();
                value = fetcherMgr.getValueFromServer(url);
                if (value != null) {
                    LOGGER.debug("value: " + value);
                }
            } catch (Exception e) {
                LOGGER.error("cannot use remote configuration: " + keyName, e);
                LOGGER.info("using local variable: " + keyName);
            }
            LOGGER.debug("download ok.");
        }

        //
        // 注入到仓库中
        //
        uyconfStoreProcessor.inject2Store(keyName, new UyconfValue(value, null));
        LOGGER.debug("inject ok.");

        //
        // Watch
        //
        if (UyClientConfig.getInstance().ENABLE_UYCONF) {
            if (watchMgr != null) {
                UyConfCommonModel uyConfCommonModel = uyconfStoreProcessor.getCommonModel(keyName);
                watchMgr.watchPath(this, uyConfCommonModel, keyName, UyConfigTypeEnum.ITEM, value);
                LOGGER.debug("watch ok.");
            } else {
                LOGGER.warn("cannot monitor {} because watch mgr is null", keyName);
            }
        }
    }

    /**
     * 更新消息:
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
            UyconfCenterItem uyconfCenterItem = (UyconfCenterItem) object;

            IUyconfUpdatePipeline iUyconfUpdatePipeline =
                    UyconfCenterStore.getInstance().getiUyconfUpdatePipeline();
            if (iUyconfUpdatePipeline != null) {
                try {
                    iUyconfUpdatePipeline.reloadUyconfItem(key, uyconfCenterItem.getValue());
                } catch (Exception e) {
                    LOGGER.error(e.toString(), e);
                }
            }
        }
    }

    /**
     * 某个配置项：注入到实例中
     */
    private void inject2OneConf(String key, UyconfCenterItem uyconfCenterItem) {

        if (uyconfCenterItem == null) {
            return;
        }

        try {

            Object object = null;

            //
            // 静态
            //
            if (!uyconfCenterItem.isStatic()) {

                object = registry.getFirstByType(uyconfCenterItem.getDeclareClass(), false, true);
            }

            uyconfStoreProcessor.inject2Instance(object, key);

        } catch (Exception e) {
            LOGGER.warn(e.toString(), e);
        }
    }

    /**
     *
     */
    @Override
    public void inject2Conf() {

        /**
         * 配置ITEM列表处理
         */
        for (String key : uyconfStoreProcessor.getConfKeySet()) {

            LOGGER.debug("==============\tstart to inject value to uyconf item instance: " + key +
                    "\t=============================");

            UyconfCenterItem uyconfCenterItem = (UyconfCenterItem) uyconfStoreProcessor.getConfData(key);

            inject2OneConf(key, uyconfCenterItem);
        }
    }

}
