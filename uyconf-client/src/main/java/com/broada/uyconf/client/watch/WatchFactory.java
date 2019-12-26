package com.broada.uyconf.client.watch;

import com.broada.uyconf.client.config.ConfigMgr;
import com.broada.uyconf.client.config.UyClientConfig;
import com.broada.uyconf.client.config.UyClientSysConfig;
import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.core.common.path.UyconfWebPathMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.watch.impl.WatchMgrImpl;

/**
 * 监控器 实例 工厂
 *
 * @author wnb
 * 14-7-29
 */
public class WatchFactory {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WatchFactory.class);

    private static String hosts = null;
    private static String zooPrefix = null;
    private static final Object hostsSync = new Object();

    /**
     * @throws Exception
     */
    public static WatchMgr getWatchMgr(FetcherMgr fetcherMgr) throws Exception {

        if (!ConfigMgr.isInit()) {
            throw new Exception("ConfigMgr should be init before WatchFactory.getWatchMgr");
        }

        if (hosts == null || zooPrefix == null) {
            synchronized(hostsSync) {
                if (hosts == null || zooPrefix == null) {

                    // 获取 Zoo Hosts
                    try {

                        hosts = fetcherMgr.getValueFromServer(UyconfWebPathMgr.getZooHostsUrl(UyClientSysConfig
                                                                                                   .getInstance()
                                                                                                   .CONF_SERVER_ZOO_ACTION));

                        zooPrefix = fetcherMgr.getValueFromServer(UyconfWebPathMgr.getZooPrefixUrl(UyClientSysConfig
                                                                                                        .getInstance
                                                                                                             ()
                                                                                                        .CONF_SERVER_ZOO_ACTION));

                        WatchMgr watchMgr = new WatchMgrImpl();
                        watchMgr.init(hosts, zooPrefix, UyClientConfig.getInstance().DEBUG);

                        return watchMgr;

                    } catch (Exception e) {

                        LOGGER.error("cannot get watch module", e);

                    }
                }
            }
        }

        return null;
    }
}
