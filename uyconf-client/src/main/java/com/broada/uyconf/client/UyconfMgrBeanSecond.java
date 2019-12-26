package com.broada.uyconf.client;

/**
 * 第二次扫描，动态扫描
 *
 * @author wnb
 *
 */
public class UyconfMgrBeanSecond {

    public void init() {

        UyconfMgr.getInstance().secondScan();
    }

    public void destroy() {
        UyconfMgr.getInstance().close();
    }
}
