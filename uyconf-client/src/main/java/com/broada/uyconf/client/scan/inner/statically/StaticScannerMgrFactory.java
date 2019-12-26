package com.broada.uyconf.client.scan.inner.statically;

import com.broada.uyconf.client.scan.inner.statically.impl.StaticScannerFileMgrImpl;
import com.broada.uyconf.client.scan.inner.statically.impl.StaticScannerItemMgrImpl;
import com.broada.uyconf.client.scan.inner.statically.impl.StaticScannerNonAnnotationFileMgrImpl;

/**
 * @author wnb
 *
 */
public class StaticScannerMgrFactory {

    /**
     * 配置文件处理

     */
    public static StaticScannerMgr getUyconfFileStaticScanner() {
        return new StaticScannerFileMgrImpl();
    }

    /**
     * 配置项处理

     */
    public static StaticScannerMgr getUyconfItemStaticScanner() {
        return new StaticScannerItemMgrImpl();
    }

    /**
     * 非注解的配置文件处理

     */
    public static StaticScannerMgr getUyconfNonAnnotationFileStaticScanner() {
        return new StaticScannerNonAnnotationFileMgrImpl();
    }

}
