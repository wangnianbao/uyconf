package com.broada.uyconf.client.core.processor;

import com.broada.uyconf.client.core.processor.impl.DisconfFileCoreProcessorImpl;
import com.broada.uyconf.client.core.processor.impl.DisconfItemCoreProcessorImpl;
import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.client.watch.WatchMgr;

/**
 * 核心处理器工厂
 *
 * @author liaoqiqi
 * @version 2014-8-4
 */
public class DisconfCoreProcessorFactory {

    /**
     * 获取配置文件核心处理器
     */
    public static DisconfCoreProcessor getDisconfCoreProcessorFile(WatchMgr watchMgr, FetcherMgr fetcherMgr, Registry
            registry) {

        return new DisconfFileCoreProcessorImpl(watchMgr, fetcherMgr, registry);
    }

    /**
     * 获取配置项核心 处理器
     */
    public static DisconfCoreProcessor getDisconfCoreProcessorItem(WatchMgr watchMgr, FetcherMgr fetcherMgr, Registry
            registry) {

        return new DisconfItemCoreProcessorImpl(watchMgr, fetcherMgr, registry);
    }
}
