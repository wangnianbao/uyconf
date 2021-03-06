package com.broada.uyconf.client.test.common;

import com.broada.uyconf.client.test.support.utils.NetUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.broada.uyconf.client.config.ConfigMgr;
import com.broada.uyconf.client.config.UyClientConfig;
import com.broada.uyconf.client.config.UyClientSysConfig;
import com.broada.uyconf.core.common.path.UyconfWebPathMgr;

import junit.framework.Assert;

/**
 * Spring的测试方法
 *
 * @author wnb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaseSpringTestCase {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseSpringTestCase.class);

    @Test
    public void pass() {

    }

    /**
     * @return
     */
    @Deprecated
    protected boolean checkNetWork() {

        //
        // 如果网络不通则认为测试通过
        //
        try {
            ConfigMgr.init();
        } catch (Exception e) {
            Assert.assertTrue(false);
        }

        if (!NetUtils.pingUrl(UyClientConfig.getInstance().getHostList().get(0) + UyconfWebPathMgr
                .getZooHostsUrl
                        (UyClientSysConfig
                                .getInstance().CONF_SERVER_ZOO_ACTION))) {
            return false;
        }

        return true;
    }

}
