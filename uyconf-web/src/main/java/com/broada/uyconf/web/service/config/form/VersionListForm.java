package com.broada.uyconf.web.service.config.form;

import javax.validation.constraints.NotNull;

/**
 * @author wnb
 * 14-6-22
 */
public class VersionListForm {

    @NotNull
    private Long appId;

    // 环境可以为空
    private Long envId;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    @Override
    public String toString() {
        return "VersionListForm [appId=" + appId + ", envId=" + envId + "]";
    }

}
