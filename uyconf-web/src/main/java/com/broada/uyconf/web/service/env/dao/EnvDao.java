package com.broada.uyconf.web.service.env.dao;

import com.broada.uyconf.web.service.env.bo.Env;
import com.broada.unbiz.common.genericdao.dao.BaseDao;

/**
 * @author wnb
 *
 */
public interface EnvDao extends BaseDao<Long, Env> {

    /**
     * @param name
     *
     * @return
     */
    Env getByName(String name);
}
