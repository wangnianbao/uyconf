package com.broada.uyconf.web.service.config.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.broada.uyconf.core.common.constants.Constants;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import com.broada.uyconf.core.common.json.ValueVo;
import com.broada.uyconf.web.service.config.bo.Config;
import com.broada.uyconf.web.service.config.dao.ConfigDao;
import com.broada.uyconf.web.service.config.service.ConfigFetchMgr;
import com.broada.uyconf.web.service.config.utils.ConfigUtils;

/**
 * @author wnb
 */
@Service
public class ConfigFetchMgrImpl implements ConfigFetchMgr {

    protected static final Logger LOG = LoggerFactory.getLogger(ConfigFetchMgrImpl.class);

    @Autowired
    private ConfigDao configDao;

    /**
     * 根据详细参数获取配置
     */
    @Override
    public Config getConfByParameter(Long appId, Long envId, String version, String key,
                                     UyConfigTypeEnum uyConfigTypeEnum) {

        return configDao.getByParameter(appId, envId, version, key, uyConfigTypeEnum);
    }

    /**
     * 根据详细参数获取配置返回
     */
    @Override
    public ValueVo getConfItemByParameter(Long appId, Long envId, String version, String key) {

        Config config = configDao.getByParameter(appId, envId, version, key, UyConfigTypeEnum.ITEM);
        if (config == null) {
            return ConfigUtils.getErrorVo("cannot find this config");
        }

        ValueVo valueVo = new ValueVo();
        valueVo.setValue(config.getValue());
        valueVo.setStatus(Constants.OK);

        return valueVo;
    }

    /**
     * 根据详细参数获取配置列表返回
     */
    @Override
    public List<Config> getConfListByParameter(Long appId, Long envId, String version, Boolean hasValue) {
        return configDao.getConfigList(appId, envId, version, hasValue);
    }

}
