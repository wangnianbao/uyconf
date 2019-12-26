package com.broada.uyconf.client.core.processor;

import com.broada.uyconf.client.core.processor.impl.UyconfFileCoreProcessorImpl;
import com.broada.uyconf.client.core.processor.impl.UyconfItemCoreProcessorImpl;
import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.client.watch.WatchMgr;

/**
 * 核心处理器工厂
 *
 * @author wnb
 * 14-8-4
 */
public class UyconfCoreProcessorFactory {

    /**
     * 获取配置文件核心处理器
     */
    public static UyconfCoreProcessor getUyconfCoreProcessorFile(WatchMgr watchMgr, FetcherMgr fetcherMgr, Registry
            registry) {

        return new UyconfFileCoreProcessorImpl(watchMgr, fetcherMgr, registry);
    }

    /**
     * 获取配置项核心 处理器
     */
    public static UyconfCoreProcessor getUyconfCoreProcessorItem(WatchMgr watchMgr, FetcherMgr fetcherMgr, Registry
            registry) {

        return new UyconfItemCoreProcessorImpl(watchMgr, fetcherMgr, registry);
    }
}
