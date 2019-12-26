package com.broada.uyconf.web.web.auth;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.broada.uyconf.web.service.sign.form.SigninForm;
import com.broada.uyconf.web.service.sign.service.SignMgr;
import com.broada.uyconf.web.service.user.bo.User;
import com.broada.uyconf.web.service.user.dto.Visitor;
import com.broada.uyconf.web.service.user.form.PasswordModifyForm;
import com.broada.uyconf.web.service.user.service.UserMgr;
import com.broada.uyconf.web.service.user.vo.VisitorVo;
import com.broada.uyconf.web.web.auth.constant.LoginConstant;
import com.broada.uyconf.web.web.auth.login.RedisLogin;
import com.broada.uyconf.web.web.auth.validator.AuthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broada.dsp.common.annotation.NoAuth;
import com.broada.dsp.common.constant.ErrorCode;
import com.broada.dsp.common.constant.WebConstants;
import com.broada.dsp.common.controller.BaseController;
import com.broada.dsp.common.vo.JsonObjectBase;
import com.broada.ub.common.commons.ThreadContext;

/**
 * @author wnb
 * 14-1-20
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/account")
public class UserController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMgr userMgr;

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private SignMgr signMgr;

    @Autowired
    private RedisLogin redisLogin;

    /**
     * GET 获取
     *
     * @param
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase get() {

        VisitorVo visitorVo = userMgr.getCurVisitor();
        if (visitorVo != null) {

            return buildSuccess("visitor", visitorVo);

        } else {

            // 没有登录啊
            return buildGlobalError("syserror.inner", ErrorCode.GLOBAL_ERROR);
        }
    }

    /**
     * 登录
     *
     * @param signin
     * @param request
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBase signin(@Valid SigninForm signin, HttpServletRequest request) {

        LOG.info(signin.toString());

        // 验证
        authValidator.validateLogin(signin);

        // 数据库登录
        User user = signMgr.signin(signin.getName());

        // 过期时间
        int expireTime = LoginConstant.SESSION_EXPIRE_TIME;
        if (signin.getRemember().equals(1)) {
            expireTime = LoginConstant.SESSION_EXPIRE_TIME2;
        }

        // redis login
        redisLogin.login(request, user, expireTime);

        VisitorVo visitorVo = userMgr.getCurVisitor();

        return buildSuccess("visitor", visitorVo);
    }

    /**
     * 登出
     *
     * @param request
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/signout", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase signout(HttpServletRequest request) {

        redisLogin.logout(request);

        return buildSuccess("ok", "ok");
    }

    /**
     * 修改密码
     *
     * @param
     *
     * @return
     */
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    @ResponseBody
    public JsonObjectBase password(@Valid PasswordModifyForm passwordModifyForm, HttpServletRequest request) {

        // 校验
        authValidator.validatePasswordModify(passwordModifyForm);

        // 修改
        Visitor visitor = ThreadContext.getSessionVisitor();
        userMgr.modifyPassword(visitor.getLoginUserId(), passwordModifyForm.getNew_password());

        // re login
        redisLogin.logout(request);

        return buildSuccess("修改成功，请重新登录");
    }
}
