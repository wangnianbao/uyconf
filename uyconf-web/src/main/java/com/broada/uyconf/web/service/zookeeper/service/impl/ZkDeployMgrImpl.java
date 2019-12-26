package com.broada.uyconf.web.service.zookeeper.service.impl;

import java.util.List;
import java.util.Map;

import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import com.broada.uyconf.web.innerapi.zookeeper.ZooKeeperDriver;
import com.broada.uyconf.web.service.zookeeper.config.ZooConfig;
import com.broada.uyconf.web.service.zookeeper.dto.ZkUyconfData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.broada.uyconf.core.common.path.ZooPathMgr;
import com.broada.uyconf.web.service.zookeeper.service.ZkDeployMgr;

/**
 * @author wnb
 *
 */
@Service
public class ZkDeployMgrImpl implements ZkDeployMgr {

    @Autowired
    private ZooKeeperDriver zooKeeperDriver;

    @Autowired
    private ZooConfig zooConfig;

    /**
     * 获取ZK的详细部署信息
     */
    @Override
    public String getDeployInfo(String app, String env, String version) {

        // 路径获取
        String url = ZooPathMgr.getZooBaseUrl(zooConfig.getZookeeperUrlPrefix(), app, env, version);

        List<String> hostInfoList = zooKeeperDriver.getConf(url);

        return StringUtils.join(hostInfoList, '\n');
    }

    /**
     * 获取每个配置级别的Map数据, Key是配置, Value是ZK配置数据
     *
     * @return
     */
    @Override
    public Map<String, ZkUyconfData> getZkUyconfDataMap(String app, String env, String version) {

        return zooKeeperDriver.getUyconfData(app, env, version);
    }

    @Override
    public ZkUyconfData getZkUyconfData(String app, String env, String version, UyConfigTypeEnum uyConfigTypeEnum,
                                        String keyName) {

        return zooKeeperDriver.getUyconfData(app, env, version, uyConfigTypeEnum, keyName);
    }

}
