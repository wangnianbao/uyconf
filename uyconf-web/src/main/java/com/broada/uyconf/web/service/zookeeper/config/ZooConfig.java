package com.broada.uyconf.web.service.zookeeper.config;

/**
 * @author wnb
 *
 */
public class ZooConfig {

    private String zooHosts = "";

    public String zookeeperUrlPrefix = "";

    public String getZooHosts() {
        return zooHosts;
    }

    public void setZooHosts(String zooHosts) {
        this.zooHosts = zooHosts;
    }

    public String getZookeeperUrlPrefix() {
        return zookeeperUrlPrefix;
    }

    public void setZookeeperUrlPrefix(String zookeeperUrlPrefix) {
        this.zookeeperUrlPrefix = zookeeperUrlPrefix;
    }
}
