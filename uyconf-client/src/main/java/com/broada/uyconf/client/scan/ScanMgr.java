package com.broada.uyconf.client.scan;

import java.util.List;

/**
 * @author wnb
 *
 */
public interface ScanMgr {

    /**
     * @throws Exception
     */
    void firstScan(List<String> packageNameLit) throws Exception;

    /**
     * @throws Exception
     */
    void secondScan() throws Exception;

    /**
     * reloadable for specify files
     *
     * @throws Exception
     */
    void reloadableScan(String fileName) throws Exception;
}
