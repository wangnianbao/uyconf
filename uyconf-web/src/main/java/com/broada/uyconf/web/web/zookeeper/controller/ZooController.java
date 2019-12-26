package com.broada.uyconf.web.web.zookeeper.controller;

import javax.validation.Valid;

import com.broada.uyconf.web.service.zookeeper.config.ZooConfig;
import com.broada.uyconf.web.service.zookeeper.form.ZkDeployForm;
import com.broada.uyconf.web.service.zookeeper.service.ZkDeployMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broada.uyconf.core.common.constants.Constants;
import com.broada.uyconf.core.common.json.ValueVo;
import com.broada.uyconf.web.web.config.dto.ConfigFullModel;
import com.broada.uyconf.web.web.zookeeper.validator.ZkDeployValidator;
import com.broada.dsp.common.annotation.NoAuth;
import com.broada.dsp.common.constant.WebConstants;
import com.broada.dsp.common.controller.BaseController;
import com.broada.dsp.common.vo.JsonObjectBase;

/**
 * Zoo API
 *
 * @author wnb
 * 14-1-20
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/zoo")
public class ZooController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(ZooController.class);

    @Autowired
    private ZooConfig zooConfig;

    @Autowired
    private ZkDeployValidator zkDeployValidator;

    @Autowired
    private ZkDeployMgr zkDeployMgr;

    /**
     * 获取Zookeeper地址
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/hosts", method = RequestMethod.GET)
    @ResponseBody
    public ValueVo getHosts() {

        ValueVo confItemVo = new ValueVo();
        confItemVo.setStatus(Constants.OK);
        confItemVo.setValue(zooConfig.getZooHosts());

        return confItemVo;
    }

    /**
     * 获取ZK prefix
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/prefix", method = RequestMethod.GET)
    @ResponseBody
    public ValueVo getPrefixUrl() {

        ValueVo confItemVo = new ValueVo();
        confItemVo.setStatus(Constants.OK);
        confItemVo.setValue(zooConfig.getZookeeperUrlPrefix());

        return confItemVo;
    }

    /**
     * 获取ZK 部署情况
     *
     * @param zkDeployForm
     *
     * @return
     */
    @RequestMapping(value = "/zkdeploy", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase getZkDeployInfo(@Valid ZkDeployForm zkDeployForm) {

        LOG.info(zkDeployForm.toString());

        ConfigFullModel configFullModel = zkDeployValidator.verify(zkDeployForm);

        String data = zkDeployMgr.getDeployInfo(configFullModel.getApp().getName(), configFullModel.getEnv().getName(),
                zkDeployForm.getVersion());

        return buildSuccess("hostInfo", data);
    }
}