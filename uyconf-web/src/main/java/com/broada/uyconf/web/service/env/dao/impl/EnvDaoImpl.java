package com.broada.uyconf.web.service.env.dao.impl;

import org.springframework.stereotype.Service;

import com.broada.uyconf.web.service.env.bo.Env;
import com.broada.uyconf.web.service.env.dao.EnvDao;
import com.broada.dsp.common.dao.AbstractDao;
import com.broada.dsp.common.dao.Columns;
import com.broada.unbiz.common.genericdao.operator.Match;

/**
 * @author wnb
 * 14-6-16
 */
@Service
public class EnvDaoImpl extends AbstractDao<Long, Env> implements EnvDao {

    @Override
    public Env getByName(String name) {

        return findOne(new Match(Columns.NAME, name));
    }

}
