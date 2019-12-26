package com.broada.uyconf.web.service.user.dao.impl;

import com.broada.uyconf.web.service.user.dao.UserDao;
import org.springframework.stereotype.Repository;

import com.broada.uyconf.web.service.user.bo.User;
import com.broada.dsp.common.dao.AbstractDao;
import com.broada.dsp.common.dao.Columns;

/**
 * @author wnb
 * 13-11-28
 */
@Repository
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {

    /**
     * 执行SQL
     */
    public void executeSql(String sql) {

        executeSQL(sql, null);
    }

    /**
     */
    @Override
    public User getUserByName(String name) {

        return findOne(match(Columns.NAME, name));
    }

}