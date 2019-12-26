package com.broada.uyconf.web.service.config.service;

import java.io.File;
import java.util.List;

import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import com.broada.uyconf.web.service.config.bo.Config;
import com.broada.uyconf.web.service.config.form.ConfListForm;
import com.broada.uyconf.web.service.config.form.ConfNewItemForm;
import com.broada.uyconf.web.service.config.vo.ConfListVo;
import com.broada.uyconf.web.service.config.vo.MachineListVo;
import com.broada.ub.common.db.DaoPageResult;

/**
 * @author wnb
 *
 */
public interface ConfigMgr {

    /**
     * @param
     *
     * @return
     */
    List<String> getVersionListByAppEnv(Long appId, Long envId);

    /**
     * @return
     */
    DaoPageResult<ConfListVo> getConfigList(ConfListForm confListForm, boolean fetchZk, final boolean getErrorMessage);

    /**
     * @param configId
     *
     * @return
     */
    ConfListVo getConfVo(Long configId);

    MachineListVo getConfVoWithZk(Long configId);

    /**
     * @param configId
     *
     * @return
     */
    Config getConfigById(Long configId);

    /**
     * 更新 配置项/配置文件
     *
     * @param configId
     *
     * @return
     */
    String updateItemValue(Long configId, String value);

    /**
     * @param configId
     *
     * @return
     */
    String getValue(Long configId);

    void notifyZookeeper(Long configId);

    /**
     * @param confNewForm
     * @param uyConfigTypeEnum
     */
    void newConfig(ConfNewItemForm confNewForm, UyConfigTypeEnum uyConfigTypeEnum);

    void delete(Long configId);

    /**
     * @param confListForm
     *
     * @return
     */
    List<File> getUyconfFileList(ConfListForm confListForm);

}
