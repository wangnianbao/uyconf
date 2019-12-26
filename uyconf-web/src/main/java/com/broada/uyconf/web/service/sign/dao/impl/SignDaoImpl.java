package com.broada.uyconf.web.service.sign.dao.impl;

import com.broada.uyconf.web.service.user.bo.User;
import org.springframework.stereotype.Repository;

import com.broada.uyconf.web.service.sign.dao.SignDao;
import com.broada.dsp.common.dao.AbstractDao;

/**
 * @author wnb
 * 13-11-28
 */
@Repository
public class SignDaoImpl extends AbstractDao<Long, User> implements SignDao {

}