package com.broada.uyconf.client.core;

import com.broada.uyconf.client.config.UyClientConfig;
import com.broada.uyconf.client.core.impl.UyconfCoreMgrImpl;
import com.broada.uyconf.client.fetcher.FetcherFactory;
import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.client.watch.WatchFactory;
import com.broada.uyconf.client.watch.WatchMgr;

/**
 * Core模块的工厂类
 *
 * @author wnb
 * 14-7-29
 */
public class UyconfCoreFactory {

    /**
     * @throws Exception
     */
    public static UyconfCoreMgr getUyconfCoreMgr(Registry registry) throws Exception {

        FetcherMgr fetcherMgr = FetcherFactory.getFetcherMgr();

        //
        // 不开启uyconf，则不要watch了
        //
        WatchMgr watchMgr = null;
        if (UyClientConfig.getInstance().ENABLE_UYCONF) {
            // Watch 模块
            watchMgr = WatchFactory.getWatchMgr(fetcherMgr);
        }

        return new UyconfCoreMgrImpl(watchMgr, fetcherMgr, registry);
    }
}
