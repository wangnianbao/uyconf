package com.broada.uyconf.client.test.watch.mock;

import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.core.processor.UyconfCoreProcessor;
import com.broada.uyconf.client.watch.WatchMgr;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;

import mockit.Mock;
import mockit.MockUp;

/**
 * Watch MOckup
 *
 * @author wnb
 * 14-7-31
 */
public class WatchMgrMock extends MockUp<WatchMgr> {

    @Mock
    public void init(String hosts, String zooUrlPrefix, boolean debug) throws Exception {
        return;
    }

    /**
     * 监控路径,监控前会事先创建路径,并且会新建一个自己的Temp子结点
     */
    @Mock
    public void watchPath(UyconfCoreProcessor uyconfCoreMgr, UyConfCommonModel uyConfCommonModel, String keyName,
                          UyConfigTypeEnum uyConfigTypeEnum, String value) throws Exception {

        return;
    }

    @Mock
    public void release() {
        return;
    }
}
