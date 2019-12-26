package com.broada.uyconf.web.innerapi.zookeeper.impl;

import java.util.List;
import java.util.Map;

import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import com.broada.uyconf.web.innerapi.zookeeper.ZooKeeperDriver;
import com.broada.uyconf.web.service.zookeeper.dto.ZkUyconfData;

/**
 * Created by wnb on 15/1/14.
 */
public class ZookeeperDriverMock implements ZooKeeperDriver {

    @Override
    public void notifyNodeUpdate(String app, String env, String version, String key, String value,
                                 UyConfigTypeEnum uyConfigTypeEnum) {

    }

    @Override
    public Map<String, ZkUyconfData> getUyconfData(String app, String env, String version) {
        return null;
    }

    @Override
    public ZkUyconfData getUyconfData(String app, String env, String version, UyConfigTypeEnum uyConfigTypeEnum,
                                      String keyName) {
        return null;
    }

    @Override
    public List<String> getConf(String groupName) {
        return null;
    }

    @Override
    public void destroy() throws Exception {

    }
}
