package com.broada.uyconf.client.core;

import com.broada.uyconf.client.config.DisClientConfig;
import com.broada.uyconf.client.core.impl.DisconfCoreMgrImpl;
import com.broada.uyconf.client.fetcher.FetcherFactory;
import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.client.watch.WatchFactory;
import com.broada.uyconf.client.watch.WatchMgr;

/**
 * Core模块的工厂类
 *
 * @author liaoqiqi
 * @version 2014-7-29
 */
public class DisconfCoreFactory {

    /**
     * @throws Exception
     */
    public static DisconfCoreMgr getDisconfCoreMgr(Registry registry) throws Exception {

        FetcherMgr fetcherMgr = FetcherFactory.getFetcherMgr();

        //
        // 不开启disconf，则不要watch了
        //
        WatchMgr watchMgr = null;
        if (DisClientConfig.getInstance().ENABLE_DISCONF) {
            // Watch 模块
            watchMgr = WatchFactory.getWatchMgr(fetcherMgr);
        }

        return new DisconfCoreMgrImpl(watchMgr, fetcherMgr, registry);
    }
}
