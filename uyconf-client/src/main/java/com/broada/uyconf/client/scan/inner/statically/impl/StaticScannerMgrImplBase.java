package com.broada.uyconf.client.scan.inner.statically.impl;

import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.config.UyClientConfig;

/**
 * @author wnb
 * 14-9-9
 */
public class StaticScannerMgrImplBase {

    /**
     * env/version 默认是应用整合设置的，但用户可以在配置中更改它
     */
    protected static UyConfCommonModel makeUyConfCommonModel(String app, String env, String version) {

        UyConfCommonModel uyConfCommonModel = new UyConfCommonModel();

        // app
        if (!app.isEmpty()) {
            uyConfCommonModel.setApp(app);
        } else {
            uyConfCommonModel.setApp(UyClientConfig.getInstance().APP);
        }

        // env
        if (!env.isEmpty()) {
            uyConfCommonModel.setEnv(env);
        } else {
            uyConfCommonModel.setEnv(UyClientConfig.getInstance().ENV);
        }

        // version
        if (!version.isEmpty()) {
            uyConfCommonModel.setVersion(version);
        } else {
            uyConfCommonModel.setVersion(UyClientConfig.getInstance().VERSION);
        }

        return uyConfCommonModel;
    }

}
