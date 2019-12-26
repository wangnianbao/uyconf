package com.broada.uyconf.web.web.config.dto;

import com.broada.uyconf.web.service.app.bo.App;
import com.broada.uyconf.web.service.env.bo.Env;

/**
 * @author wnb
 *
 */
public class ConfigFullModel {

    private App app;
    private Env env;
    private String version;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public Env getEnv() {
        return env;
    }

    public void setEnv(Env env) {
        this.env = env;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ConfigFullModel(App app, Env env, String version, String key) {
        super();
        this.app = app;
        this.env = env;
        this.version = version;
        this.key = key;
    }
}
