package com.broada.uyconf.web.web.env.controller;

import java.util.List;

import com.broada.uyconf.web.service.env.service.EnvMgr;
import com.broada.uyconf.web.service.env.vo.EnvListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broada.dsp.common.constant.WebConstants;
import com.broada.dsp.common.controller.BaseController;
import com.broada.dsp.common.vo.JsonObjectBase;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/env")
public class EnvController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(EnvController.class);

    @Autowired
    private EnvMgr envMgr;

    /**
     * list
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase list() {

        List<EnvListVo> envListVos = envMgr.getVoList();

        return buildListSuccess(envListVos, envListVos.size());
    }

}
