package com.broada.uyconf.web.service.config.service;

/**
 * Created by wnb on 15/12/25.
 */
public interface ConfigHistoryMgr {

    void createOne(Long configId, String oldValue, String newValue);
}
