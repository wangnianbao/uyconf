package com.broada.uyconf.client.scan.inner.statically;

import java.util.Set;

import com.broada.uyconf.client.scan.inner.statically.model.ScanStaticModel;

/**
 * @author wnb
 *
 */
public interface StaticScannerMgr {

    void scanData2Store(ScanStaticModel scanModel);

    void exclude(Set<String> keySet);
}
