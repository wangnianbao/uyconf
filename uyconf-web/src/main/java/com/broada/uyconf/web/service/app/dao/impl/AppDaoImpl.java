package com.broada.uyconf.web.service.app.dao.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.broada.uyconf.web.service.app.bo.App;
import com.broada.uyconf.web.service.app.dao.AppDao;
import com.broada.dsp.common.dao.AbstractDao;
import com.broada.dsp.common.dao.Columns;
import com.broada.unbiz.common.genericdao.operator.Match;

/**
 * @author wnb
 *
 */
@Service
public class AppDaoImpl extends AbstractDao<Long, App> implements AppDao {

    @Override
    public App getByName(String name) {

        return findOne(new Match(Columns.NAME, name));
    }

    @Override
    public List<App> getByIds(Set<Long> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            return findAll();
        }

        return find(match(Columns.APP_ID, ids));
    }

}
