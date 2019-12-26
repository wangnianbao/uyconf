package com.broada.uyconf.client.test.model;

import com.broada.uyconf.client.common.annotations.UyconfFile;
import com.broada.uyconf.client.common.annotations.UyconfFileItem;

/**
 * 静态配置文件
 *
 * @author wnb
 * 14-8-14
 */
@UyconfFile(filename = "staticConf.properties")
public class StaticConf {

    private static int staticvar = 40;

    private static double staticvar2 = 50;

    @UyconfFileItem(name = "staticvar", associateField = "staticvar")
    public static int getStaticVar() {
        return staticvar;
    }

    public static void setStaticVar(int staticVar) {
        StaticConf.staticvar = staticVar;
    }

    public static int getStaticvar() {
        return staticvar;
    }

    public static void setStaticvar(int staticvar) {
        StaticConf.staticvar = staticvar;
    }

    @UyconfFileItem(name = "staticvar2", associateField = "staticvar2")
    public static double getStaticvar2() {
        return staticvar2;
    }

    public static void setStaticvar2(double staticvar2) {
        StaticConf.staticvar2 = staticvar2;
    }
}
