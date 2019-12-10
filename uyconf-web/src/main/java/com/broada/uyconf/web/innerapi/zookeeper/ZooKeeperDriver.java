package com.broada.uyconf.web.innerapi.zookeeper;

import java.util.List;
import java.util.Map;

import com.broada.uyconf.core.common.constants.DisConfigTypeEnum;
import com.broada.uyconf.web.service.zookeeper.dto.ZkDisconfData;

/**
 * @author liaoqiqi
 * @version 2014-6-24
 */
public interface ZooKeeperDriver {

    /**
     * 通知某个Node更新
     *
     * @param app
     * @param env
     * @param version
     * @param disConfigTypeEnum
     */
    void notifyNodeUpdate(String app, String env, String version, String key, String value,
                          DisConfigTypeEnum disConfigTypeEnum);

    /**
     * 获取分布式配置 Map
     *
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    Map<String, ZkDisconfData> getDisconfData(String app, String env, String version);

    /**
     * 获取分布式配置 Map
     *
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    ZkDisconfData getDisconfData(String app, String env, String version, DisConfigTypeEnum disConfigTypeEnum,
                                 String keyName);

    /**
     * 返回groupName结点向下的所有zookeeper信息
     */
    List<String> getConf(String groupName);

    void destroy() throws Exception;

}
