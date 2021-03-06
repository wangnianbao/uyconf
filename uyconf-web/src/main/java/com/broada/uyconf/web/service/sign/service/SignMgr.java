package com.broada.uyconf.web.service.sign.service;

import com.broada.uyconf.web.service.user.bo.User;

/**
 * @author wnb
 *
 */
public interface SignMgr {

    User getUserByName(String name);

    boolean validate(String userPassword, String passwordToBeValidate);

    User signin(String phone);
}
