package com.broada.uyconf.web.web.auth.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.broada.uyconf.web.service.user.bo.User;
import com.broada.uyconf.web.service.user.dto.Visitor;

/**
 * @author wnb
 *
 */
public interface RedisLogin {

    /**
     * 判断是否登录
     *
     * @param request
     *
     * @return
     */
    Visitor isLogin(HttpServletRequest request);

    /**
     * 登录
     *
     * @param request
     */
    void login(HttpServletRequest request, User user, int expireTime);

    /**
     * 更新session数据
     *
     * @param session
     * @param visitor
     */
    void updateSessionVisitor(HttpSession session, Visitor visitor);

    void logout(HttpServletRequest request);
}
