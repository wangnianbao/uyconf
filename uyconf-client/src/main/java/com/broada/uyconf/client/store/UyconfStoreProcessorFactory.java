package com.broada.uyconf.client.store;

import com.broada.uyconf.client.store.processor.impl.UyconfStoreFileProcessorImpl;
import com.broada.uyconf.client.store.processor.impl.UyconfStoreItemProcessorImpl;
import com.broada.uyconf.client.store.processor.impl.UyconfStorePipelineProcessorImpl;

/**
 * 仓库算子仓库
 *
 * @author wnb
 *
 */
public class UyconfStoreProcessorFactory {

    /**
     * 获取配置文件仓库算子
     */
    public static UyconfStoreProcessor getUyconfStoreFileProcessor() {

        return new UyconfStoreFileProcessorImpl();
    }

    /**
     * 获取配置项仓库算子
     */
    public static UyconfStoreProcessor getUyconfStoreItemProcessor() {

        return new UyconfStoreItemProcessorImpl();
    }

    /**
     *
     */
    public static UyconfStorePipelineProcessor getUyconfStorePipelineProcessor() {

        return new UyconfStorePipelineProcessorImpl();
    }

}
