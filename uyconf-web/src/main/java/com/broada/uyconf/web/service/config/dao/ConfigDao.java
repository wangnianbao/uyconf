package com.broada.uyconf.web.service.config.dao;

import java.util.List;

import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import com.broada.uyconf.web.service.config.bo.Config;
import com.broada.dsp.common.form.RequestListBase.Page;
import com.broada.ub.common.db.DaoPageResult;
import com.broada.unbiz.common.genericdao.dao.BaseDao;

/**
 * @author wnb
 *
 */
public interface ConfigDao extends BaseDao<Long, Config> {

    /**
     * @param appId
     * @param envId
     * @param version
     * @param key
     * @param uyConfigTypeEnum
     *
     * @return
     */
    Config getByParameter(Long appId, Long envId, String version, String key, UyConfigTypeEnum uyConfigTypeEnum);

    /**
     * @param
     *
     * @return
     */
    List<Config> getConfByAppEnv(Long appId, Long envId);

    /**
     * @param appId
     * @param envId
     * @param version
     *
     * @return
     */
    DaoPageResult<Config> getConfigList(Long appId, Long envId, String version, Page page);

    /**
     * @param configId
     *
     * @return
     */
    void updateValue(Long configId, String value);

    /**
     *
     */
    String getValue(Long configId);

    /**
     * @param appId
     * @param envId
     * @param version
     * @param hasValue
     * @return
     */
    List<Config> getConfigList(Long appId, Long envId, String version, Boolean hasValue);


    /**
     * @param configId
     */
    void deleteItem(Long configId);
}
