package com.broada.uyconf.web.web.config.validator;

import com.broada.uyconf.web.service.app.bo.App;
import com.broada.uyconf.web.service.app.service.AppMgr;
import com.broada.uyconf.web.service.config.form.ConfForm;
import com.broada.uyconf.web.service.config.service.ConfigMgr;
import com.broada.uyconf.web.service.env.bo.Env;
import com.broada.uyconf.web.service.env.service.EnvMgr;
import com.broada.uyconf.web.web.config.dto.ConfigFullModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.broada.dsp.common.exception.FieldException;

/**
 * @author wnb
 */
@Service
public class ConfigValidator4Fetch {

    @Autowired
    private AppMgr appMgr;

    @Autowired
    private EnvMgr envMgr;

    @Autowired
    private ConfigMgr configMgr;

    /**
     * 此接口是客户的接口，非常 重要，目前没有权限的控制
     *
     * @param confForm
     */
    public ConfigFullModel verifyConfForm(ConfForm confForm, boolean unCheckKey) {

        //
        // app
        //
        if (StringUtils.isEmpty(confForm.getApp())) {
            throw new FieldException("app", "app is empty", null);
        }

        App app = appMgr.getByName(confForm.getApp());
        if (app == null) {
            throw new FieldException("app", "app " + confForm.getApp() + " doesn't exist in db.", null);
        }

        //
        // env
        //
        if (StringUtils.isEmpty(confForm.getEnv())) {
            throw new FieldException("env", "env is empty", null);
        }

        Env env = envMgr.getByName(confForm.getEnv());
        if (env == null) {
            throw new FieldException("env", "env " + confForm.getEnv() + " doesn't exist in db.", null);
        }

        //
        // key
        //
        if (!unCheckKey && StringUtils.isEmpty(confForm.getKey())) {
            throw new FieldException("key", "key is empty", null);
        }

        //
        // version
        //
        if (StringUtils.isEmpty(confForm.getVersion())) {
            throw new FieldException("version", "version is empty", null);
        }

        return new ConfigFullModel(app, env, confForm.getVersion(), confForm.getKey());
    }
}
