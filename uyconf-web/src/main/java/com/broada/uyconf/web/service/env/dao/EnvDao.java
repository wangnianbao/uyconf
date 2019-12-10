package com.broada.uyconf.web.service.env.dao;

import com.broada.uyconf.web.service.env.bo.Env;
import com.broada.unbiz.common.genericdao.dao.BaseDao;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
public interface EnvDao extends BaseDao<Long, Env> {

    /**
     * @param name
     *
     * @return
     */
    Env getByName(String name);
}
