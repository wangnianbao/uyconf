package com.broada.uyconf.web.service.role.service;

import java.util.List;

import com.broada.uyconf.web.service.role.bo.Role;

/**
 * @author wnb
 *  2013-12-24
 */
public interface RoleMgr {

    public Role get(Integer roleId);

    public List<Role> findAll();

}
