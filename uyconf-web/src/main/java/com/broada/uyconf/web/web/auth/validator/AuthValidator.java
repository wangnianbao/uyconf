package com.broada.uyconf.web.web.auth.validator;

import com.broada.uyconf.web.service.sign.form.SigninForm;
import com.broada.uyconf.web.service.sign.service.SignMgr;
import com.broada.uyconf.web.service.user.bo.User;
import com.broada.uyconf.web.service.user.dto.Visitor;
import com.broada.uyconf.web.service.user.form.PasswordModifyForm;
import com.broada.uyconf.web.service.user.service.UserMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.broada.dsp.common.exception.FieldException;
import com.broada.ub.common.commons.ThreadContext;

/**
 * 权限验证
 *
 * @author wnb
 * 14-7-2
 */
@Component
public class AuthValidator {

    @Autowired
    private SignMgr signMgr;

    @Autowired
    private UserMgr userMgr;

    /**
     * 验证登录
     */
    public void validateLogin(SigninForm signinForm) {

        //
        // 校验用户是否存在
        //
        User user = signMgr.getUserByName(signinForm.getName());
        if (user == null) {
            throw new FieldException(SigninForm.Name, "user.not.exist", null);
        }

        // 校验密码
        if (!signMgr.validate(user.getPassword(), signinForm.getPassword())) {
            throw new FieldException(SigninForm.PASSWORD, "password.not.right", null);
        }
    }

    /**
     * 验证密码更新
     */
    public void validatePasswordModify(PasswordModifyForm passwordModifyForm) {

        Visitor visitor = ThreadContext.getSessionVisitor();

        User user = userMgr.getUser(visitor.getLoginUserId());

        // 校验密码
        if (!signMgr.validate(user.getPassword(), passwordModifyForm.getOld_password())) {
            throw new FieldException(PasswordModifyForm.OLD_PASSWORD, "password.not.right", null);
        }

        if (!passwordModifyForm.getNew_password().equals(passwordModifyForm.getNew_password_2())) {
            throw new FieldException(PasswordModifyForm.NEW_PASSWORD, "two.password.not.equal", null);
        }
    }
}
