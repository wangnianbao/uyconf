package com.broada.uyconf.web.service.config.service;

/**
 * Created by wnb
 */
public interface ConfigHistoryMgr {

    void createOne(Long configId, String oldValue, String newValue);
}
