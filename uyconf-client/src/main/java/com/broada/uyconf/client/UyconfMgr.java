package com.broada.uyconf.client;

import java.util.List;

import com.broada.uyconf.client.config.ConfigMgr;
import com.broada.uyconf.client.config.UyClientConfig;
import com.broada.uyconf.client.core.UyconfCoreFactory;
import com.broada.uyconf.client.core.UyconfCoreMgr;
import com.broada.uyconf.client.scan.ScanFactory;
import com.broada.uyconf.client.scan.ScanMgr;
import com.broada.uyconf.client.store.UyconfStoreProcessorFactory;
import com.broada.uyconf.client.support.registry.Registry;
import com.broada.uyconf.client.support.registry.RegistryFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Uyconf Client 总入口
 *
 * @author wnb
 *
 */
public class UyconfMgr implements ApplicationContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfMgr.class);

    // 本实例不能初始化两次
    private boolean isFirstInit = false;
    private boolean isSecondInit = false;

    // application context
    private ApplicationContext applicationContext;

    // 核心处理器
    private UyconfCoreMgr uyconfCoreMgr = null;

    // scan mgr
    private ScanMgr scanMgr = null;

    protected static final UyconfMgr INSTANCE = new UyconfMgr();

    public static UyconfMgr getInstance() {
        return INSTANCE;
    }

    private UyconfMgr() {

    }

    /**
     * 总入口
     */
    public synchronized void start(List<String> scanPackageList) {

        firstScan(scanPackageList);

        secondScan();
    }

    /**
     * 第一次扫描，静态扫描 for annotation config
     */
    protected synchronized void firstScan(List<String> scanPackageList) {

        // 该函数不能调用两次
        if (isFirstInit) {
            LOGGER.info("UyConfMgr has been init, ignore........");
            return;
        }

        //
        //
        //

        try {

            // 导入配置
            ConfigMgr.init();

            LOGGER.info("******************************* UYCONF START FIRST SCAN *******************************");

            // registry
            Registry registry = RegistryFactory.getSpringRegistry(applicationContext);

            // 扫描器
            scanMgr = ScanFactory.getScanMgr(registry);

            // 第一次扫描并入库
            scanMgr.firstScan(scanPackageList);

            // 获取数据/注入/Watch
            uyconfCoreMgr = UyconfCoreFactory.getUyconfCoreMgr(registry);
            uyconfCoreMgr.process();

            //
            isFirstInit = true;

            LOGGER.info("******************************* UYCONF END FIRST SCAN *******************************");

        } catch (Exception e) {

            LOGGER.error(e.toString(), e);
        }
    }

    /**
     * 第二次扫描, 动态扫描, for annotation config
     */
    protected synchronized void secondScan() {

        // 该函数必须第一次运行后才能运行
        if (!isFirstInit) {
            LOGGER.info("should run First Scan before Second Scan.");
            return;
        }

        // 第二次扫描也只能做一次
        if (isSecondInit) {
            LOGGER.info("should not run twice.");
            return;
        }

        LOGGER.info("******************************* UYCONF START SECOND SCAN *******************************");

        try {

            // 扫描回调函数
            if (scanMgr != null) {
                scanMgr.secondScan();
            }

            // 注入数据至配置实体中
            // 获取数据/注入/Watch
            if (uyconfCoreMgr != null) {
                uyconfCoreMgr.inject2UyconfInstance();
            }

        } catch (Exception e) {
            LOGGER.error(e.toString(), e);
        }

        isSecondInit = true;

        //
        // 不开启 则不要打印变量map
        //
        if (UyClientConfig.getInstance().ENABLE_UYCONF) {

            //
            String data = UyconfStoreProcessorFactory.getUyconfStoreFileProcessor()
                    .confToString();
            if (!StringUtils.isEmpty(data)) {
                LOGGER.info("Conf File Map: {}", data);
            }

            //
            data = UyconfStoreProcessorFactory.getUyconfStoreItemProcessor()
                    .confToString();
            if (!StringUtils.isEmpty(data)) {
                LOGGER.info("Conf Item Map: {}", data);
            }
        }
        LOGGER.info("******************************* UYCONF END *******************************");
    }

    /**
     * reloadable config file scan, for xml config
     */
    public synchronized void reloadableScan(String fileName) {

        if (!isFirstInit) {
            return;
        }

        if (UyClientConfig.getInstance().ENABLE_UYCONF) {
            try {

                if (!UyClientConfig.getInstance().getIgnoreUyconfKeySet().contains(fileName)) {

                    if (scanMgr != null) {
                        scanMgr.reloadableScan(fileName);
                    }

                    if (uyconfCoreMgr != null) {
                        uyconfCoreMgr.processFile(fileName);
                    }
                    LOGGER.debug("uyconf reloadable file: {}", fileName);
                }

            } catch (Exception e) {

                LOGGER.error(e.toString(), e);
            }
        }
    }

    /**
     * @Description: 总关闭
     */
    public synchronized void close() {

        try {

            // uyconfCoreMgr
            LOGGER.info("******************************* uyconf CLOSE *******************************");
            if (uyconfCoreMgr != null) {
                uyconfCoreMgr.release();
            }

            // close, 必须将其设置为False,以便重新更新
            isFirstInit = false;
            isSecondInit = false;

        } catch (Exception e) {

            LOGGER.error("UyConfMgr close Failed.", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
