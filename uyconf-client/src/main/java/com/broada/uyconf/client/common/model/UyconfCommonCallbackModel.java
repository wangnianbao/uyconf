package com.broada.uyconf.client.common.model;

import java.util.ArrayList;
import java.util.List;

import com.broada.uyconf.client.common.update.IUyconfUpdate;

/**
 * 记录配置更新时 或 主备切换时的回调函数
 *
 * @author wnb
 * 14-5-22
 */
public class UyconfCommonCallbackModel {

    /**
     * 所有用户自己的回调函数(配置更新时)
     */
    private List<IUyconfUpdate> uyconfConfUpdates = new ArrayList<IUyconfUpdate>();

    /**
     *  所有用户自己的回调函数(主备切换时)
     */
    private List<IUyconfUpdate> uyconfUpdatesActiveBackups = new ArrayList<IUyconfUpdate>();

    public List<IUyconfUpdate> getUyconfConfUpdates() {
        return uyconfConfUpdates;
    }

    public void setUyconfConfUpdates(List<IUyconfUpdate> uyconfConfUpdates) {
        this.uyconfConfUpdates = uyconfConfUpdates;
    }

    public List<IUyconfUpdate> getUyconfUpdatesActiveBackups() {
        return uyconfUpdatesActiveBackups;
    }

    public void setUyconfUpdatesActiveBackups(List<IUyconfUpdate> uyconfUpdatesActiveBackups) {
        this.uyconfUpdatesActiveBackups = uyconfUpdatesActiveBackups;
    }

    @Override
    public String toString() {
        return "UyconfCommonCallbackModel{" +
                "uyconfConfUpdates=" + uyconfConfUpdates +
                ", uyconfUpdatesActiveBackups=" + uyconfUpdatesActiveBackups +
                '}';
    }
}
