package com.broada.uyconf.client.common.model;

/**
 * 配置基类
 *
 * @author wnb
 * 14-8-4
 */
public abstract class UyconfCenterBaseModel {

    // 所在类实体
    private Object object;

    // 远程配置服务的URL路径,不包含IP和PORT的
    private String remoteServerUrl;

    // 通用配置
    private UyConfCommonModel uyConfCommonModel = new UyConfCommonModel();

    // 回调函数
    private UyconfCommonCallbackModel uyconfCommonCallbackModel = new UyconfCommonCallbackModel();

    public UyConfCommonModel getUyConfCommonModel() {
        return uyConfCommonModel;
    }

    public void setUyConfCommonModel(UyConfCommonModel uyConfCommonModel) {
        this.uyConfCommonModel = uyConfCommonModel;
    }

    public UyconfCommonCallbackModel getUyconfCommonCallbackModel() {
        return uyconfCommonCallbackModel;
    }

    public void setUyconfCommonCallbackModel(UyconfCommonCallbackModel uyconfCommonCallbackModel) {
        this.uyconfCommonCallbackModel = uyconfCommonCallbackModel;
    }

    public String getRemoteServerUrl() {
        return remoteServerUrl;
    }

    public void setRemoteServerUrl(String remoteServerUrl) {
        this.remoteServerUrl = remoteServerUrl;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "\n\tUyconfCenterBaseModel [\n\tobject=" + object + "\n\tremoteServerUrl=" + remoteServerUrl +
                   "\n\tuyConfCommonModel=" + uyConfCommonModel + "\n\tuyconfCommonCallbackModel=" +
                uyconfCommonCallbackModel + "]";
    }

    public String infoString() {
        return "\n\tremoteServerUrl=" + remoteServerUrl;
    }
}
