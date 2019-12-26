package com.broada.uyconf.client.watch.impl;

import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.config.inner.UyClientComConfig;
import com.broada.uyconf.client.core.processor.UyconfCoreProcessor;
import com.broada.uyconf.client.watch.inner.UyconfSysUpdateCallback;
import com.broada.uyconf.client.watch.inner.NodeWatcher;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.watch.WatchMgr;
import com.broada.uyconf.core.common.path.ZooPathMgr;
import com.broada.uyconf.core.common.utils.ZooUtils;
import com.broada.uyconf.core.common.zookeeper.ZookeeperMgr;

/**
 * Watch 模块的一个实现
 *
 * @author wnb
 *
 */
public class WatchMgrImpl implements WatchMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WatchMgrImpl.class);

    /**
     * zoo prefix
     */
    private String zooUrlPrefix;

    /**
     *
     */
    private boolean debug;

    /**
     * @Description: 获取自己的主备类型
     */
    @Override
    public void init(String hosts, String zooUrlPrefix, boolean debug) throws Exception {

        this.zooUrlPrefix = zooUrlPrefix;
        this.debug = debug;

        // init zookeeper
        ZookeeperMgr.getInstance().init(hosts, zooUrlPrefix, debug);
    }

    /**
     * 新建监控
     *
     * @throws Exception
     */
    private String makeMonitorPath(UyConfigTypeEnum uyConfigTypeEnum, UyConfCommonModel uyConfCommonModel,
                                   String key, String value) throws Exception {

        // 应用根目录
        /*
            应用程序的 Zoo 根目录
        */
        String clientRootZooPath = ZooPathMgr.getZooBaseUrl(zooUrlPrefix, uyConfCommonModel.getApp(),
                uyConfCommonModel.getEnv(),
                uyConfCommonModel.getVersion());
        ZookeeperMgr.getInstance().makeDir(clientRootZooPath, ZooUtils.getIp());

        // 监控路径
        String monitorPath;
        if (uyConfigTypeEnum.equals(UyConfigTypeEnum.FILE)) {

            // 新建Zoo Store目录
            String clientUyconfFileZooPath = ZooPathMgr.getFileZooPath(clientRootZooPath);
            makePath(clientUyconfFileZooPath, ZooUtils.getIp());

            monitorPath = ZooPathMgr.joinPath(clientUyconfFileZooPath, key);

        } else {

            // 新建Zoo Store目录
            String clientUyconfItemZooPath = ZooPathMgr.getItemZooPath(clientRootZooPath);
            makePath(clientUyconfItemZooPath, ZooUtils.getIp());
            monitorPath = ZooPathMgr.joinPath(clientUyconfItemZooPath, key);
        }

        // 先新建路径
        makePath(monitorPath, "");

        // 新建一个代表自己的临时结点
        makeTempChildPath(monitorPath, value);

        return monitorPath;
    }

    /**
     * 创建路径
     */
    private void makePath(String path, String data) {

        ZookeeperMgr.getInstance().makeDir(path, data);
    }

    /**
     * 在指定路径下创建一个临时结点
     */
    private void makeTempChildPath(String path, String data) {

        String finerPrint = UyClientComConfig.getInstance().getInstanceFingerprint();

        String mainTypeFullStr = path + "/" + finerPrint;
        try {
            ZookeeperMgr.getInstance().createEphemeralNode(mainTypeFullStr, data, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            LOGGER.error("cannot create: " + mainTypeFullStr + "\t" + e.toString());
        }
    }

    /**
     * 监控路径,监控前会事先创建路径,并且会新建一个自己的Temp子结点
     */
    @Override
    public void watchPath(UyconfCoreProcessor uyconfCoreMgr, UyConfCommonModel uyConfCommonModel, String keyName,
                          UyConfigTypeEnum uyConfigTypeEnum, String value) throws Exception {

        // 新建
        String monitorPath = makeMonitorPath(uyConfigTypeEnum, uyConfCommonModel, keyName, value);

        // 进行监控
        NodeWatcher nodeWatcher =
                new NodeWatcher(uyconfCoreMgr, monitorPath, keyName, uyConfigTypeEnum, new UyconfSysUpdateCallback(),
                        debug);
        nodeWatcher.monitorMaster();
    }

    @Override
    public void release() {

        try {
            ZookeeperMgr.getInstance().release();
        } catch (InterruptedException e) {

            LOGGER.error(e.toString());
        }
    }

}
