package com.broada.uyconf.web.service.sign.service.impl;

import com.broada.uyconf.web.service.user.bo.User;
import com.broada.uyconf.web.service.user.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.broada.uyconf.web.service.sign.dao.SignDao;
import com.broada.uyconf.web.service.sign.service.SignMgr;
import com.broada.uyconf.web.service.sign.utils.SignUtils;

/**
 * 与登录登出相关的
 *
 * @author wnb
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SignMgrImpl implements SignMgr {

    protected static final Logger LOG = LoggerFactory.getLogger(SignMgrImpl.class);

    @Autowired
    private SignDao signDao;

    @Autowired
    private UserDao userDao;

    /**
     * 根据 用户名获取用户
     */
    @Override
    public User getUserByName(String name) {

        return userDao.getUserByName(name);
    }

    /**
     * 验证密码是否正确
     *
     * @param userPassword
     * @param passwordToBeValidate
     *
     * @return
     */
    public boolean validate(String userPassword, String passwordToBeValidate) {

        String data = SignUtils.createPassword(passwordToBeValidate);

        if (data.equals(userPassword)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 登录
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public User signin(String phone) {

        //
        // 获取用户
        //
        User user = userDao.getUserByName(phone);

        userDao.update(user);

        return user;
    }

}
