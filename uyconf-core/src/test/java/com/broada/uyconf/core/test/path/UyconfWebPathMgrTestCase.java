package com.broada.uyconf.core.test.path;

import com.broada.uyconf.core.common.path.UyconfWebPathMgr;
import com.broada.uyconf.core.test.common.BaseCoreTestCase;
import org.junit.Assert;
import org.junit.Test;

import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;

/**
 * @author wnb
 * 14-6-16
 */
public class UyconfWebPathMgrTestCase extends BaseCoreTestCase {

    @Test
    public void getZooPrefixUrlTest() {

        String result = UyconfWebPathMgr.getZooPrefixUrl("/test");

        Assert.assertEquals("/test/prefix", result);
    }

    @Test
    public void getZooHostsUrlTest() {

        String result = UyconfWebPathMgr.getZooHostsUrl("/test");

        Assert.assertEquals("/test/hosts", result);
    }

    @Test
    public void getRemoteUrlParameterTest() {

        String fileUrl =
                UyconfWebPathMgr.getRemoteUrlParameter("test", "app", "version", "env", "key", UyConfigTypeEnum.FILE);
        System.out.println(fileUrl);
        Assert.assertEquals("test/file?version=version&app=app&env=env&key=key&type=0", fileUrl);

        String itemUrl =
                UyconfWebPathMgr.getRemoteUrlParameter("test", "app", "version", "env", "key", UyConfigTypeEnum.ITEM);
        System.out.println(fileUrl);
        Assert.assertEquals("test/item?version=version&app=app&env=env&key=key&type=1", itemUrl);
    }
}
