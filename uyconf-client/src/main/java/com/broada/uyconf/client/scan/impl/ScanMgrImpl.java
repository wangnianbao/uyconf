package com.broada.uyconf.client.scan.impl;

import java.util.ArrayList;
import java.util.List;

import com.broada.uyconf.client.config.UyClientConfig;
import com.broada.uyconf.client.scan.inner.dynamic.ScanDynamicStoreAdapter;
import com.broada.uyconf.client.scan.inner.statically.StaticScannerMgr;
import com.broada.uyconf.client.scan.inner.statically.StaticScannerMgrFactory;
import com.broada.uyconf.client.scan.inner.statically.impl.StaticScannerNonAnnotationFileMgrImpl;
import com.broada.uyconf.client.scan.inner.statically.model.ScanStaticModel;
import com.broada.uyconf.client.scan.inner.statically.strategy.ScanStaticStrategy;
import com.broada.uyconf.client.scan.inner.statically.strategy.impl.ReflectionScanStatic;
import com.broada.uyconf.client.store.inner.UyconfCenterHostFilesStore;
import com.broada.uyconf.client.support.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.scan.ScanMgr;

/**
 * 扫描模块
 *
 * @author wnb
 *
 */
public class ScanMgrImpl implements ScanMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ScanMgrImpl.class);

    // 扫描对象
    private volatile ScanStaticModel scanModel = null;

    //
    private Registry registry = null;

    private List<StaticScannerMgr> staticScannerMgrList = new ArrayList<StaticScannerMgr>();

    private ScanStaticStrategy scanStaticStrategy = new ReflectionScanStatic();

    /**
     *
     */
    public ScanMgrImpl(Registry registry) {

        this.registry = registry;

        // 配置文件
        staticScannerMgrList.add(StaticScannerMgrFactory.getUyconfFileStaticScanner());

        // 配置项
        staticScannerMgrList.add(StaticScannerMgrFactory.getUyconfItemStaticScanner());

        // 非注解 托管的配置文件
        staticScannerMgrList.add(StaticScannerMgrFactory.getUyconfNonAnnotationFileStaticScanner());
    }

    /**
     * 扫描并存储(静态)
     *
     * @throws Exception
     */
    public void firstScan(List<String> packageNameList) throws Exception {

        LOGGER.debug("start to scan package: " + packageNameList.toString());

        // 获取扫描对象并分析整合
        scanModel = scanStaticStrategy.scan(packageNameList);

        // 增加非注解的配置
        scanModel.setJustHostFiles(UyconfCenterHostFilesStore.getInstance().getJustHostFiles());

        // 放进仓库
        for (StaticScannerMgr scannerMgr : staticScannerMgrList) {

            // 扫描进入仓库
            scannerMgr.scanData2Store(scanModel);

            // 忽略哪些KEY
            scannerMgr.exclude(UyClientConfig.getInstance().getIgnoreUyconfKeySet());
        }
    }

    /**
     * 第二次扫描(动态)
     */
    public void secondScan() throws Exception {

        // 开启uyconf才需要处理回调
        if (UyClientConfig.getInstance().ENABLE_UYCONF) {

            if (scanModel == null) {
                synchronized(scanModel) {
                    // 下载模块必须先初始化
                    if (scanModel == null) {
                        throw new Exception("You should run first scan before second Scan");
                    }
                }
            }

            // 将回调函数实例化并写入仓库
            ScanDynamicStoreAdapter.scanUpdateCallbacks(scanModel, registry);
        }
    }

    /**
     * reloadable file scan
     *
     * @throws Exception
     */
    @Override
    public void reloadableScan(String fileName) throws Exception {

        StaticScannerNonAnnotationFileMgrImpl.scanData2Store(fileName);
    }

}
