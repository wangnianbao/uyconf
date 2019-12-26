package com.broada.uyconf.web.service.user.dao;

import com.broada.uyconf.web.service.user.bo.User;
import com.broada.unbiz.common.genericdao.dao.BaseDao;

/**
 * @author wnb
 * 13-11-28
 */
public interface UserDao extends BaseDao<Long, User> {

    void executeSql(String sql);

    User getUserByName(String name);

}
