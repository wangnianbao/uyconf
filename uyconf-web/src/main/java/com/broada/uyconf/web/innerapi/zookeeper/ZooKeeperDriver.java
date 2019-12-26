package com.broada.uyconf.web.innerapi.zookeeper;

import java.util.List;
import java.util.Map;

import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import com.broada.uyconf.web.service.zookeeper.dto.ZkUyconfData;

/**
 * @author wnb
 *
 */
public interface ZooKeeperDriver {

    /**
     * 通知某个Node更新
     *
     * @param app
     * @param env
     * @param version
     * @param uyConfigTypeEnum
     */
    void notifyNodeUpdate(String app, String env, String version, String key, String value,
                          UyConfigTypeEnum uyConfigTypeEnum);

    /**
     * 获取分布式配置 Map
     *
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    Map<String, ZkUyconfData> getUyconfData(String app, String env, String version);

    /**
     * 获取分布式配置 Map
     *
     * @param app
     * @param env
     * @param version
     *
     * @return
     */
    ZkUyconfData getUyconfData(String app, String env, String version, UyConfigTypeEnum uyConfigTypeEnum,
                               String keyName);

    /**
     * 返回groupName结点向下的所有zookeeper信息
     */
    List<String> getConf(String groupName);

    void destroy() throws Exception;

}
