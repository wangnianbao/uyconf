package com.broada.uyconf.client.scan;

import com.broada.uyconf.client.scan.impl.ScanMgrImpl;
import com.broada.uyconf.client.support.registry.Registry;

/**
 * 扫描器工厂
 *
 * @author wnb
 * 14-7-29
 */
public class ScanFactory {

    /**
     * @throws Exception
     */
    public static ScanMgr getScanMgr(Registry registry) throws Exception {

        ScanMgr scanMgr = new ScanMgrImpl(registry);
        return scanMgr;
    }
}
