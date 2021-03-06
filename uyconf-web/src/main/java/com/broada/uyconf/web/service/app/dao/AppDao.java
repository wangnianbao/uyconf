package com.broada.uyconf.web.service.app.dao;

import java.util.List;
import java.util.Set;

import com.broada.uyconf.web.service.app.bo.App;
import com.broada.unbiz.common.genericdao.dao.BaseDao;

/**
 * @author wnb
 *
 */
public interface AppDao extends BaseDao<Long, App> {

    /**
     * @param name
     *
     * @return
     */
    App getByName(String name);

    /**
     * @param ids
     *
     * @return
     */
    List<App> getByIds(Set<Long> ids);

}
