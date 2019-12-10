package com.broada.uyconf.client.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.broada.uyconf.client.core.DisconfCoreMgr;
import com.broada.uyconf.client.core.processor.DisconfCoreProcessor;
import com.broada.uyconf.client.core.processor.DisconfCoreProcessorFactory;
import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.client.watch.WatchMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 核心处理器
 *
 * @author liaoqiqi
 * @version 2014-6-10
 */
public class DisconfCoreMgrImpl implements DisconfCoreMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfCoreMgrImpl.class);

    private List<DisconfCoreProcessor> disconfCoreProcessorList = new ArrayList<DisconfCoreProcessor>();

    // 监控器
    private WatchMgr watchMgr = null;

    // 抓取器
    private FetcherMgr fetcherMgr = null;

    // registry
    private Registry registry = null;

    public DisconfCoreMgrImpl(WatchMgr watchMgr, FetcherMgr fetcherMgr, Registry registry) {

        this.watchMgr = watchMgr;
        this.fetcherMgr = fetcherMgr;
        this.registry = registry;

        //
        // 在这里添加好配置项、配置文件的处理器
        //
        DisconfCoreProcessor disconfCoreProcessorFile =
                DisconfCoreProcessorFactory.getDisconfCoreProcessorFile(watchMgr, fetcherMgr, registry);
        disconfCoreProcessorList.add(disconfCoreProcessorFile);

        DisconfCoreProcessor disconfCoreProcessorItem =
                DisconfCoreProcessorFactory.getDisconfCoreProcessorItem(watchMgr, fetcherMgr, registry);
        disconfCoreProcessorList.add(disconfCoreProcessorItem);
    }

    /**
     * 1. 获取远程的所有配置数据<br/>
     * 2. 注入到仓库中<br/>
     * 3. Watch 配置 <br/>
     * <p/>
     * 更新 所有配置数据
     */
    public void process() {

        //
        // 处理
        //
        for (DisconfCoreProcessor disconfCoreProcessor : disconfCoreProcessorList) {

            disconfCoreProcessor.processAllItems();
        }
    }

    /**
     * 只处理某一个
     */
    @Override
    public void processFile(String fileName) {

        DisconfCoreProcessor disconfCoreProcessorFile =
                DisconfCoreProcessorFactory.getDisconfCoreProcessorFile(watchMgr, fetcherMgr, registry);

        disconfCoreProcessorFile.processOneItem(fileName);
    }

    /**
     * 特殊的，将仓库里的数据注入到 配置项、配置文件 的实体中
     */
    public void inject2DisconfInstance() {

        //
        // 处理
        //
        for (DisconfCoreProcessor disconfCoreProcessor : disconfCoreProcessorList) {

            disconfCoreProcessor.inject2Conf();
        }
    }

    @Override
    public void release() {

        if (fetcherMgr != null) {
            fetcherMgr.release();
        }

        if (watchMgr != null) {
            watchMgr.release();
        }
    }
}
