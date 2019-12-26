package com.broada.uyconf.client.scan.inner.statically.model;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import com.broada.uyconf.client.common.update.IUyconfUpdatePipeline;
import org.reflections.Reflections;

/**
 * 扫描静态存储的对象
 *
 * @author wnb
 *
 */
public class ScanStaticModel {

    private Reflections reflections;

    /**
     *  配置文件
     */
    private Set<Class<?>> uyconfFileClassSet;

    /**
     * 配置文件中的函数
     */
    private Set<Method> uyconfFileItemMethodSet;

    /**
     *  配置文件及其函数的MAP, KEY为配置文件类
     */
    private Map<Class<?>, Set<Method>> uyconfFileItemMap;

    /**
     * 配置ITEM
     */
    private Set<Method> uyconfItemMethodSet;

    /**
     * 主从切换的回调函数类
     */
    private Set<Class<?>> uyconfActiveBackupServiceClassSet;

    /**
     * 更新 回调函数类
     */
    private Set<Class<?>> uyconfUpdateService;
    private Class<IUyconfUpdatePipeline> iUyconfUpdatePipeline = null;

    /**
     * 只是托管的配置文件，没有注入到类中
     */
    private Set<String> justHostFiles;

    /**
     *  reload files
     */
    private Set<String> reloadableFiles;

    public Reflections getReflections() {
        return reflections;
    }

    public void setReflections(Reflections reflections) {
        this.reflections = reflections;
    }

    public Set<Class<?>> getUyconfFileClassSet() {
        return uyconfFileClassSet;
    }

    public void setUyconfFileClassSet(Set<Class<?>> uyconfFileClassSet) {
        this.uyconfFileClassSet = uyconfFileClassSet;
    }

    public Map<Class<?>, Set<Method>> getUyconfFileItemMap() {
        return uyconfFileItemMap;
    }

    public void setUyconfFileItemMap(Map<Class<?>, Set<Method>> uyconfFileItemMap) {
        this.uyconfFileItemMap = uyconfFileItemMap;
    }

    public Set<Method> getUyconfItemMethodSet() {
        return uyconfItemMethodSet;
    }

    public void setUyconfItemMethodSet(Set<Method> uyconfItemMethodSet) {
        this.uyconfItemMethodSet = uyconfItemMethodSet;
    }

    public Set<Method> getUyconfFileItemMethodSet() {
        return uyconfFileItemMethodSet;
    }

    public void setUyconfFileItemMethodSet(Set<Method> uyconfFileItemMethodSet) {
        this.uyconfFileItemMethodSet = uyconfFileItemMethodSet;
    }

    public Set<Class<?>> getUyconfActiveBackupServiceClassSet() {
        return uyconfActiveBackupServiceClassSet;
    }

    public void setUyconfActiveBackupServiceClassSet(Set<Class<?>> uyconfActiveBackupServiceClassSet) {
        this.uyconfActiveBackupServiceClassSet = uyconfActiveBackupServiceClassSet;
    }

    public Set<Class<?>> getUyconfUpdateService() {
        return uyconfUpdateService;
    }

    public void setUyconfUpdateService(Set<Class<?>> uyconfUpdateService) {
        this.uyconfUpdateService = uyconfUpdateService;
    }

    public Set<String> getReloadableFiles() {
        return reloadableFiles;
    }

    public void setReloadableFiles(Set<String> reloadableFiles) {
        this.reloadableFiles = reloadableFiles;
    }

    public Set<String> getJustHostFiles() {
        return justHostFiles;
    }

    public void setJustHostFiles(Set<String> justHostFiles) {
        this.justHostFiles = justHostFiles;
    }

    public Class<IUyconfUpdatePipeline> getiUyconfUpdatePipeline() {
        return iUyconfUpdatePipeline;
    }

    public void setiUyconfUpdatePipeline(
            Class<IUyconfUpdatePipeline> iUyconfUpdatePipeline) {
        this.iUyconfUpdatePipeline = iUyconfUpdatePipeline;
    }

    @Override
    public String toString() {
        return "ScanStaticModel{" +
                "reflections=" + reflections +
                ", uyconfFileClassSet=" + uyconfFileClassSet +
                ", uyconfFileItemMethodSet=" + uyconfFileItemMethodSet +
                ", uyconfFileItemMap=" + uyconfFileItemMap +
                ", uyconfItemMethodSet=" + uyconfItemMethodSet +
                ", iUyconfUpdatePipeline=" + iUyconfUpdatePipeline +
                ", uyconfActiveBackupServiceClassSet=" + uyconfActiveBackupServiceClassSet +
                ", uyconfUpdateService=" + uyconfUpdateService +
                ", justHostFiles=" + justHostFiles +
                ", reloadableFiles=" + reloadableFiles +
                '}';
    }

}
