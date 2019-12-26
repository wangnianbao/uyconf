package com.broada.uyconf.client.test.scan.inner;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.broada.uyconf.client.UyconfMgrBean;
import com.broada.uyconf.client.test.common.BaseSpringTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.scan.inner.statically.model.ScanStaticModel;
import com.broada.uyconf.client.scan.inner.statically.strategy.ScanStaticStrategy;
import com.broada.uyconf.client.scan.inner.statically.strategy.impl.ReflectionScanStatic;
import com.broada.uyconf.client.support.utils.ScanPrinterUtils;
import com.broada.uyconf.client.support.utils.StringUtil;

/**
 * 扫描测试
 *
 * @author wnb
 * 14-6-16
 */
public class ScanPackTestCase extends BaseSpringTestCase {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ScanPackTestCase.class);

    public static final String SCAN_PACK_NAME = "com.baidu.uyconf.client.test";
    public static final List<String> SCAN_PACK_NAME_LIST =
            StringUtil.parseStringToStringList(SCAN_PACK_NAME, UyconfMgrBean.SCAN_SPLIT_TOKEN);

    @Test
    public void scan() {

        try {

            ScanStaticStrategy scanStaticStrategy = new ReflectionScanStatic();

            ScanStaticModel scanModel = scanStaticStrategy.scan(SCAN_PACK_NAME_LIST);

            // PRINT SCAN STORE
            ScanPrinterUtils.printStoreMap(scanModel.getReflections());

            // uyconf file item
            LOGGER.info("=============UYCONF FILE ITEM===================");
            Set<Method> methods = scanModel.getUyconfFileItemMethodSet();
            ScanPrinterUtils.printFileItemMethod(methods);
            Assert.assertEquals(6, methods.size());
            Assert.assertEquals(4, scanModel.getUyconfFileClassSet().size());

            // uyconf file item
            LOGGER.info("=============UYCONF FILE===================");
            Map<Class<?>, Set<Method>> fileMap = scanModel.getUyconfFileItemMap();
            Assert.assertEquals(4, fileMap.size());

            // uyconf item
            LOGGER.info("=============UYCONF ITEM===================");
            methods = scanModel.getUyconfItemMethodSet();
            ScanPrinterUtils.printFileItemMethod(methods);
            Assert.assertEquals(1, methods.size());

            // Active backup
            LOGGER.info("=============UYCONF ACTIVE BACKUP===================");
            Set<Class<?>> classSet = scanModel.getUyconfActiveBackupServiceClassSet();
            ScanPrinterUtils.printActiveBackup(classSet);
            Assert.assertEquals(0, classSet.size());

            // Update service
            LOGGER.info("=============UYCONF Update service===================");
            classSet = scanModel.getUyconfUpdateService();
            ScanPrinterUtils.printUpdateFile(classSet);
            Assert.assertEquals(2, classSet.size());

        } catch (Exception e) {

            Assert.assertTrue(false);
        }
    }
}
