package com.broada.uyconf.web.service.role.service;

import java.util.List;

import com.broada.uyconf.web.service.role.bo.Role;

/**
 * @author wnb
 *  204
 */
public interface RoleMgr {

    public Role get(Integer roleId);

    public List<Role> findAll();

}
