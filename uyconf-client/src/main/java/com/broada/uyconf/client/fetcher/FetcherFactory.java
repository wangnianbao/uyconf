package com.broada.uyconf.client.fetcher;

import com.broada.uyconf.client.config.ConfigMgr;
import com.broada.uyconf.client.config.UyClientConfig;
import com.broada.uyconf.client.config.UyClientSysConfig;
import com.broada.uyconf.client.fetcher.impl.FetcherMgrImpl;
import com.broada.uyconf.core.common.restful.RestfulFactory;
import com.broada.uyconf.core.common.restful.RestfulMgr;

/**
 * 抓取器工厂
 *
 * @author wnb
 * 14-7-29
 */
public class FetcherFactory {

    /**
     * 获取抓取器实例，记得释放资源, 它依赖Conf模块
     */
    public static FetcherMgr getFetcherMgr() throws Exception {

        if (!ConfigMgr.isInit()) {
            throw new Exception("ConfigMgr should be init before FetcherFactory.getFetcherMgr");
        }

        // 获取一个默认的抓取器
        RestfulMgr restfulMgr = RestfulFactory.getRestfulMgrNomal();

        FetcherMgr fetcherMgr =
                new FetcherMgrImpl(restfulMgr, UyClientConfig.getInstance().CONF_SERVER_URL_RETRY_TIMES,
                        UyClientConfig.getInstance().confServerUrlRetrySleepSeconds,
                        UyClientConfig.getInstance().enableLocalDownloadDirInClassPath,
                        UyClientConfig.getInstance().userDefineDownloadDir,
                        UyClientSysConfig.getInstance().LOCAL_DOWNLOAD_DIR,
                        UyClientConfig.getInstance().getHostList());

        return fetcherMgr;
    }
}
