package com.broada.uyconf.web.service.env.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.broada.uyconf.web.service.env.bo.Env;
import com.broada.uyconf.web.service.env.vo.EnvListVo;

/**
 * @author wnb
 *
 */
public interface EnvMgr {
    /**
     * @param name
     */
    Env getByName(String name);

    /**
     * @return
     */
    List<Env> getList();

    /**
     * @return
     */
    List<EnvListVo> getVoList();

    /**
     * @param ids
     *
     * @return
     */
    Map<Long, Env> getByIds(Set<Long> ids);

    /**
     * @param id
     *
     * @return
     */
    Env getById(Long id);
}
