package com.broada.uyconf.web.service.zookeeper.service;

import java.util.Map;

import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import com.broada.uyconf.web.service.zookeeper.dto.ZkUyconfData;

/**
 * @author wnb
 *
 */
public interface ZkDeployMgr {

    /**
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    String getDeployInfo(String app, String env, String version);

    /**
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    Map<String, ZkUyconfData> getZkUyconfDataMap(String app, String env, String version);

    /**
     * 获取指定的数据
     *
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    ZkUyconfData getZkUyconfData(String app, String env, String version, UyConfigTypeEnum uyConfigTypeEnum,
                                 String keyName);
}
