package com.broada.uyconf.client.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.broada.uyconf.client.core.UyconfCoreMgr;
import com.broada.uyconf.client.core.processor.UyconfCoreProcessor;
import com.broada.uyconf.client.core.processor.UyconfCoreProcessorFactory;
import com.broada.uyconf.client.fetcher.FetcherMgr;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.client.watch.WatchMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 核心处理器
 *
 * @author wnb
 *
 */
public class UyconfCoreMgrImpl implements UyconfCoreMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfCoreMgrImpl.class);

    private List<UyconfCoreProcessor> uyconfCoreProcessorList = new ArrayList<UyconfCoreProcessor>();

    // 监控器
    private WatchMgr watchMgr = null;

    // 抓取器
    private FetcherMgr fetcherMgr = null;

    // registry
    private Registry registry = null;

    public UyconfCoreMgrImpl(WatchMgr watchMgr, FetcherMgr fetcherMgr, Registry registry) {

        this.watchMgr = watchMgr;
        this.fetcherMgr = fetcherMgr;
        this.registry = registry;

        //
        // 在这里添加好配置项、配置文件的处理器
        //
        UyconfCoreProcessor uyconfCoreProcessorFile =
                UyconfCoreProcessorFactory.getUyconfCoreProcessorFile(watchMgr, fetcherMgr, registry);
        uyconfCoreProcessorList.add(uyconfCoreProcessorFile);

        UyconfCoreProcessor uyconfCoreProcessorItem =
                UyconfCoreProcessorFactory.getUyconfCoreProcessorItem(watchMgr, fetcherMgr, registry);
        uyconfCoreProcessorList.add(uyconfCoreProcessorItem);
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
        for (UyconfCoreProcessor uyconfCoreProcessor : uyconfCoreProcessorList) {

            uyconfCoreProcessor.processAllItems();
        }
    }

    /**
     * 只处理某一个
     */
    @Override
    public void processFile(String fileName) {

        UyconfCoreProcessor uyconfCoreProcessorFile =
                UyconfCoreProcessorFactory.getUyconfCoreProcessorFile(watchMgr, fetcherMgr, registry);

        uyconfCoreProcessorFile.processOneItem(fileName);
    }

    /**
     * 特殊的，将仓库里的数据注入到 配置项、配置文件 的实体中
     */
    public void inject2UyconfInstance() {

        //
        // 处理
        //
        for (UyconfCoreProcessor uyconfCoreProcessor : uyconfCoreProcessorList) {

            uyconfCoreProcessor.inject2Conf();
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
