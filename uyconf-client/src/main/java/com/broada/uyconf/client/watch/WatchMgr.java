package com.broada.uyconf.client.watch;

import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.core.processor.UyconfCoreProcessor;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;

/**
 * 监控的接口
 *
 * @author wnb
 *
 */
public interface WatchMgr {

    /**
     * 初始化
     *
     * @throws Exception
     */
    void init(String hosts, String zooUrlPrefix, boolean debug) throws Exception;

    /**
     * 监控路径,监控前会事先创建路径,并且会新建一个自己的Temp子结点
     */
    void watchPath(UyconfCoreProcessor uyconfCoreProcessor, UyConfCommonModel uyConfCommonModel, String keyName,
                   UyConfigTypeEnum uyConfigTypeEnum, String value) throws Exception;

    void release();
}
