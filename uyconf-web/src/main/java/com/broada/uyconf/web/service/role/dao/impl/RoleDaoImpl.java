package com.broada.uyconf.web.service.role.dao.impl;

import org.springframework.stereotype.Repository;

import com.broada.uyconf.web.service.role.bo.Role;
import com.broada.uyconf.web.service.role.dao.RoleDao;
import com.broada.dsp.common.dao.AbstractDao;

/**
 * @author wnb
 *
 */
@Repository
public class RoleDaoImpl extends AbstractDao<Integer, Role> implements RoleDao {

}
