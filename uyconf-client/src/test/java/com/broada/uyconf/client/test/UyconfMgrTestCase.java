package com.broada.uyconf.client.test;

import java.util.HashSet;
import java.util.Set;

import com.broada.uyconf.client.test.common.BaseSpringMockTestCase;
import com.broada.uyconf.client.test.model.ConfA;
import com.broada.uyconf.client.test.model.ServiceA;
import com.broada.uyconf.client.test.model.StaticConf;
import com.broada.uyconf.client.test.scan.inner.ScanPackTestCase;
import com.broada.uyconf.client.test.watch.mock.WatchMgrMock;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.broada.uyconf.client.UyconfMgr;
import com.broada.uyconf.client.UyconfMgrBean;
import com.broada.uyconf.client.core.UyconfCoreFactory;
import com.broada.uyconf.client.core.UyconfCoreMgr;
import com.broada.uyconf.client.core.impl.UyconfCoreMgrImpl;
import com.broada.uyconf.client.fetcher.FetcherFactory;
import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.client.store.UyconfStoreProcessorFactory;
import com.broada.uyconf.client.store.inner.UyconfCenterHostFilesStore;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.client.support.utils.StringUtil;
import com.broada.uyconf.client.usertools.UyconfDataGetter;
import com.broada.uyconf.client.watch.WatchMgr;

import mockit.Mock;
import mockit.MockUp;

/**
 * 一个Demo示例, 远程的下载服务器使用WireMOck, Watch模块使用Jmockit
 *
 * @author wnb
 * 14-6-10
 */
public class UyconfMgrTestCase extends BaseSpringMockTestCase implements ApplicationContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfMgrTestCase.class);

    // application context
    private ApplicationContext applicationContext;

    @Autowired
    private ConfA confA;

    @Autowired
    private ServiceA serviceA;

    @Test
    public void demo() {

        //
        // mock up factory method
        //
        new MockUp<UyconfCoreFactory>() {

            @Mock
            public UyconfCoreMgr getUyconfCoreMgr(Registry registry) throws Exception {

                FetcherMgr fetcherMgr = FetcherFactory.getFetcherMgr();

                // Watch 模块
                final WatchMgr watchMgr = new WatchMgrMock().getMockInstance();
                watchMgr.init("", "", true);

                // registry
                UyconfCoreMgr uyconfCoreMgr = new UyconfCoreMgrImpl(watchMgr, fetcherMgr,
                        registry);

                return uyconfCoreMgr;
            }
        };

        //
        // 正式测试
        //
        try {

            LOGGER.info("================ BEFORE UYCONF ==============================");

            LOGGER.info("before uyconf values:");
            LOGGER.info(String.valueOf("varA: " + confA.getVarA()));
            LOGGER.info(String.valueOf("varA2: " + confA.getVarA2()));
            LOGGER.info(String.valueOf("varAA: " + serviceA.getVarAA()));

            LOGGER.info("================ BEFORE UYCONF ==============================");

            //
            // start it
            //
            Set<String> fileSet = new HashSet<String>();
            fileSet.add("atomserverl.properties");
            fileSet.add("atomserverm_slave.properties");
            UyconfCenterHostFilesStore.getInstance().addJustHostFileSet(fileSet);

            UyconfMgr.getInstance().setApplicationContext(applicationContext);
            UyconfMgr.getInstance().start(StringUtil.parseStringToStringList(ScanPackTestCase.SCAN_PACK_NAME,
                    UyconfMgrBean.SCAN_SPLIT_TOKEN));

            //
            LOGGER.info(UyconfStoreProcessorFactory.getUyconfStoreFileProcessor().confToString());

            //
            LOGGER.info(UyconfStoreProcessorFactory.getUyconfStoreItemProcessor().confToString());

            LOGGER.info("================ AFTER UYCONF ==============================");

            LOGGER.info(String.valueOf("varA: " + confA.getVarA()));
            Assert.assertEquals(new Long(1000), confA.getVarA());

            LOGGER.info(String.valueOf("varA2: " + confA.getVarA2()));
            Assert.assertEquals(new Long(2000), confA.getVarA2());

            LOGGER.info(String.valueOf("varAA: " + serviceA.getVarAA()));
            Assert.assertEquals(new Integer(1000).intValue(), serviceA.getVarAA());

            LOGGER.info(String.valueOf("static var: " + StaticConf.getStaticvar()));
            Assert.assertEquals(new Integer(50).intValue(), StaticConf.getStaticvar());

            testDynamicGetter();

            LOGGER.info("================ AFTER UYCONF ==============================");

        } catch (Exception e) {

            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    private void testDynamicGetter() {

        Assert.assertEquals(UyconfDataGetter.getByFile("confA.properties").get("confa.varA").toString(),
                "1000");

        Assert.assertEquals(UyconfDataGetter.getByItem("keyA").toString(),
                "1000");

        Assert.assertEquals(UyconfDataGetter.getByFileItem("confA.properties", "confa.varA").toString(),
                "1000");
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
