package com.broada.uyconf.web.service.config.service;

import com.broada.uyconf.core.common.constants.DisConfigTypeEnum;
import com.broada.uyconf.core.common.json.ValueVo;
import com.broada.uyconf.web.service.config.bo.Config;

import java.util.List;

/**
 * @author knightliao
 */
public interface ConfigFetchMgr {

    /**
     * @param appId
     * @param envId
     * @param envId
     * @param key
     *
     * @return
     */
    ValueVo getConfItemByParameter(Long appId, Long envId, String version, String key);

    /**
     * @param appId
     * @param envId
     * @param version
     * @param key
     * @param disConfigTypeEnum
     *
     * @return
     */
    Config getConfByParameter(Long appId, Long envId, String version, String key, DisConfigTypeEnum disConfigTypeEnum);


    /**
     * @param appId
     * @param envId
     * @param env
     *
     * @return
     */
    List<Config> getConfListByParameter(Long appId, Long envId, String env, Boolean hasValue);

}
