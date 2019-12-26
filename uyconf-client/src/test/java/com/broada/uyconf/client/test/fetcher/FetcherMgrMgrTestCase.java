package com.broada.uyconf.client.test.fetcher;

import java.util.ArrayList;

import com.broada.uyconf.client.test.common.BaseSpringTestCase;
import com.broada.uyconf.client.test.fetcher.inner.restful.RestfulMgrMock;
import org.junit.Test;

import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.client.fetcher.impl.FetcherMgrImpl;
import com.broada.uyconf.core.common.restful.RestfulMgr;

import junit.framework.Assert;

/**
 * FetcherMgrMgr测试 (采用Jmockit方法测试)
 *
 * @author wnb
 *
 */
public class FetcherMgrMgrTestCase extends BaseSpringTestCase {

    private static String requestUrl = "/url";

    /**
     * 验证获取数据的接口
     *
     * @throws Exception
     */
    @Test
    public void testGetValueFromServer() throws Exception {

        final RestfulMgr restfulMgr = new RestfulMgrMock().getMockInstance();

        FetcherMgr fetcherMgr = new FetcherMgrImpl(restfulMgr, 3, 5, true, "", "", new ArrayList<String>());

        try {

            String valueString = fetcherMgr.getValueFromServer(requestUrl);
            Assert.assertEquals(RestfulMgrMock.defaultValue, valueString);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    /**
     * 验证下载文件的接口
     *
     * @throws Exception
     */
    @Test
    public void testDownloadFileFromServer() throws Exception {

        final RestfulMgr restfulMgr = new RestfulMgrMock().getMockInstance();

        FetcherMgr fetcherMgr = new FetcherMgrImpl(restfulMgr, 3, 5, true, "", "", new ArrayList<String>());

        try {

            String valueString = fetcherMgr.downloadFileFromServer(requestUrl, RestfulMgrMock.defaultFileName,
                    "./uyconf");
            Assert.assertEquals(RestfulMgrMock.defaultFileName, valueString);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
}
