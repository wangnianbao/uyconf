package com.broada.uyconf.web.service.user.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.broada.uyconf.web.service.user.facade.UserFacade;
import com.broada.uyconf.web.service.user.service.UserMgr;

/**
 * @author wnb
 *
 */
@Service
public class UserFacadeImpl implements UserFacade {

    protected static final Logger LOG = LoggerFactory.getLogger(UserFacadeImpl.class);

    @Autowired
    private UserMgr userMgr;

}
