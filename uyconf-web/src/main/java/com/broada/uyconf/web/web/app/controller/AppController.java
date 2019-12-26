package com.broada.uyconf.web.web.app.controller;

import java.util.List;

import javax.validation.Valid;

import com.broada.uyconf.web.service.app.form.AppNewForm;
import com.broada.uyconf.web.service.app.service.AppMgr;
import com.broada.uyconf.web.service.app.vo.AppListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broada.uyconf.web.web.app.validator.AppValidator;
import com.broada.dsp.common.constant.WebConstants;
import com.broada.dsp.common.controller.BaseController;
import com.broada.dsp.common.vo.JsonObjectBase;

/**
 * @author wnb
 * 14-6-16
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/app")
public class AppController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppMgr appMgr;

    @Autowired
    private AppValidator appValidator;

    /**
     * list
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase list() {

        List<AppListVo> appListVos = appMgr.getAuthAppVoList();

        return buildListSuccess(appListVos, appListVos.size());
    }

    /**
     * create
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBase create(@Valid AppNewForm appNewForm) {

        LOG.info(appNewForm.toString());

        appValidator.validateCreate(appNewForm);

        appMgr.create(appNewForm);

        return buildSuccess("创建成功");
    }

}
