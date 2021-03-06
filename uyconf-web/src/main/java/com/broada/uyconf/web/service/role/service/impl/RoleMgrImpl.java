package com.broada.uyconf.web.service.role.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.broada.uyconf.web.service.role.bo.Role;
import com.broada.uyconf.web.service.role.dao.RoleDao;
import com.broada.uyconf.web.service.role.service.RoleMgr;

/**
 * @author wnb
 */
@Service
public class RoleMgrImpl implements RoleMgr {

    @Autowired
    private RoleDao roleDao;

    @Override
    public Role get(Integer roleId) {
        return roleDao.get(roleId);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

}
