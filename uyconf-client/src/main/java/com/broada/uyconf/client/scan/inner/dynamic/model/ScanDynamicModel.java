package com.broada.uyconf.client.scan.inner.dynamic.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.broada.uyconf.client.common.model.UyconfKey;
import com.broada.uyconf.client.common.update.IUyconfUpdate;
import com.broada.uyconf.client.common.update.IUyconfUpdatePipeline;

/**
 * 动态扫描对象
 *
 * @author wnb
 *
 */
public class ScanDynamicModel {

    /**
     * 配置及影响的回调函数, Key为配置项KEY 或 配置文件
     */
    private Map<UyconfKey, List<IUyconfUpdate>> uyconfUpdateServiceInverseIndexMap =
            new HashMap<UyconfKey, List<IUyconfUpdate>>();

    private IUyconfUpdatePipeline uyconfUpdatePipeline;

    public Map<UyconfKey, List<IUyconfUpdate>> getUyconfUpdateServiceInverseIndexMap() {
        return uyconfUpdateServiceInverseIndexMap;
    }

    public IUyconfUpdatePipeline getUyconfUpdatePipeline() {
        return uyconfUpdatePipeline;
    }

    public void setUyconfUpdatePipeline(
            IUyconfUpdatePipeline uyconfUpdatePipeline) {
        this.uyconfUpdatePipeline = uyconfUpdatePipeline;
    }

    public void setUyconfUpdateServiceInverseIndexMap(Map<UyconfKey,
            List<IUyconfUpdate>>
                                                              uyconfUpdateServiceInverseIndexMap) {
        this.uyconfUpdateServiceInverseIndexMap = uyconfUpdateServiceInverseIndexMap;
    }

    @Override
    public String toString() {
        return "ScanDynamicModel{" +
                "uyconfUpdateServiceInverseIndexMap=" + uyconfUpdateServiceInverseIndexMap +
                ", uyconfUpdatePipeline=" + uyconfUpdatePipeline +
                '}';
    }
}
