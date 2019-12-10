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

import com.broada.uyconf.client.DisconfMgr;
import com.broada.uyconf.client.DisconfMgrBean;
import com.broada.uyconf.client.core.DisconfCoreFactory;
import com.broada.uyconf.client.core.DisconfCoreMgr;
import com.broada.uyconf.client.core.impl.DisconfCoreMgrImpl;
import com.broada.uyconf.client.fetcher.FetcherFactory;
import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.client.store.DisconfStoreProcessorFactory;
import com.broada.uyconf.client.store.inner.DisconfCenterHostFilesStore;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.client.support.utils.StringUtil;
import com.broada.uyconf.client.usertools.DisconfDataGetter;
import com.broada.uyconf.client.watch.WatchMgr;

import mockit.Mock;
import mockit.MockUp;

/**
 * 一个Demo示例, 远程的下载服务器使用WireMOck, Watch模块使用Jmockit
 *
 * @author liaoqiqi
 * @version 2014-6-10
 */
public class DisconfMgrTestCase extends BaseSpringMockTestCase implements ApplicationContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfMgrTestCase.class);

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
        new MockUp<DisconfCoreFactory>() {

            @Mock
            public DisconfCoreMgr getDisconfCoreMgr(Registry registry) throws Exception {

                FetcherMgr fetcherMgr = FetcherFactory.getFetcherMgr();

                // Watch 模块
                final WatchMgr watchMgr = new WatchMgrMock().getMockInstance();
                watchMgr.init("", "", true);

                // registry
                DisconfCoreMgr disconfCoreMgr = new DisconfCoreMgrImpl(watchMgr, fetcherMgr,
                        registry);

                return disconfCoreMgr;
            }
        };

        //
        // 正式测试
        //
        try {

            LOGGER.info("================ BEFORE DISCONF ==============================");

            LOGGER.info("before disconf values:");
            LOGGER.info(String.valueOf("varA: " + confA.getVarA()));
            LOGGER.info(String.valueOf("varA2: " + confA.getVarA2()));
            LOGGER.info(String.valueOf("varAA: " + serviceA.getVarAA()));

            LOGGER.info("================ BEFORE DISCONF ==============================");

            //
            // start it
            //
            Set<String> fileSet = new HashSet<String>();
            fileSet.add("atomserverl.properties");
            fileSet.add("atomserverm_slave.properties");
            DisconfCenterHostFilesStore.getInstance().addJustHostFileSet(fileSet);

            DisconfMgr.getInstance().setApplicationContext(applicationContext);
            DisconfMgr.getInstance().start(StringUtil.parseStringToStringList(ScanPackTestCase.SCAN_PACK_NAME,
                    DisconfMgrBean.SCAN_SPLIT_TOKEN));

            //
            LOGGER.info(DisconfStoreProcessorFactory.getDisconfStoreFileProcessor().confToString());

            //
            LOGGER.info(DisconfStoreProcessorFactory.getDisconfStoreItemProcessor().confToString());

            LOGGER.info("================ AFTER DISCONF ==============================");

            LOGGER.info(String.valueOf("varA: " + confA.getVarA()));
            Assert.assertEquals(new Long(1000), confA.getVarA());

            LOGGER.info(String.valueOf("varA2: " + confA.getVarA2()));
            Assert.assertEquals(new Long(2000), confA.getVarA2());

            LOGGER.info(String.valueOf("varAA: " + serviceA.getVarAA()));
            Assert.assertEquals(new Integer(1000).intValue(), serviceA.getVarAA());

            LOGGER.info(String.valueOf("static var: " + StaticConf.getStaticvar()));
            Assert.assertEquals(new Integer(50).intValue(), StaticConf.getStaticvar());

            testDynamicGetter();

            LOGGER.info("================ AFTER DISCONF ==============================");

        } catch (Exception e) {

            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    private void testDynamicGetter() {

        Assert.assertEquals(DisconfDataGetter.getByFile("confA.properties").get("confa.varA").toString(),
                "1000");

        Assert.assertEquals(DisconfDataGetter.getByItem("keyA").toString(),
                "1000");

        Assert.assertEquals(DisconfDataGetter.getByFileItem("confA.properties", "confa.varA").toString(),
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
