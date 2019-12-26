package com.broada.uyconf.core.common.restful;

import com.broada.uyconf.core.common.restful.impl.RestfulMgrImpl;
import com.broada.uyconf.core.common.restful.retry.impl.RetryStrategyRoundBin;

/**
 * @author wnb
 *
 */
public class RestfulFactory {

    /**
     * 获取一个默认的抓取器
     *
     * @return
     *
     * @throws Exception
     */
    public static RestfulMgr getRestfulMgrNomal() throws Exception {

        RestfulMgr restfulMgr = new RestfulMgrImpl(new RetryStrategyRoundBin());

        return restfulMgr;
    }
}
