package com.broada.uyconf.web.web.zookeeper.validator;

import com.broada.uyconf.web.service.app.bo.App;
import com.broada.uyconf.web.service.app.service.AppMgr;
import com.broada.uyconf.web.service.env.bo.Env;
import com.broada.uyconf.web.service.env.service.EnvMgr;
import com.broada.uyconf.web.service.zookeeper.form.ZkDeployForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.broada.uyconf.web.web.config.dto.ConfigFullModel;
import com.broada.dsp.common.exception.FieldException;

/**
 * @author wnb
 *
 */
@Service
public class ZkDeployValidator {

    @Autowired
    private AppMgr appMgr;

    @Autowired
    private EnvMgr envMgr;

    /**
     * @param zkDeployForm
     *
     * @return
     */
    public ConfigFullModel verify(ZkDeployForm zkDeployForm) {

        //
        // app
        //
        if (zkDeployForm.getAppId() == null) {
            throw new FieldException("app is empty", null);
        }

        App app = appMgr.getById(zkDeployForm.getAppId());
        if (app == null) {
            throw new FieldException("app " + zkDeployForm.getAppId() + " doesn't exist in db.", null);
        }

        //
        // env
        //
        if (zkDeployForm.getEnvId() == null) {
            throw new FieldException("app is empty", null);
        }

        Env env = envMgr.getById(zkDeployForm.getEnvId());
        if (env == null) {
            throw new FieldException("env " + zkDeployForm.getEnvId() + " doesn't exist in db.", null);
        }

        //
        // version
        //
        if (StringUtils.isEmpty(zkDeployForm.getVersion())) {
            throw new FieldException("version is empty", null);
        }

        return new ConfigFullModel(app, env, zkDeployForm.getVersion(), "");
    }
}
