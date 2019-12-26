package com.broada.uyconf.web.service.env.bo;

import com.broada.dsp.common.dao.Columns;
import com.broada.dsp.common.dao.DB;
import com.broada.unbiz.common.genericdao.annotation.Column;
import com.broada.unbiz.common.genericdao.annotation.Table;
import com.github.knightliao.apollo.db.bo.BaseObject;

/**
 * @author wnb
 *
 */
@Table(db = DB.DB_NAME, name = "env", keyColumn = Columns.ENV_ID)
public class Env extends BaseObject<Long> {

    /**
     *
     */
    private static final long serialVersionUID = -665241738023640732L;

    /**
     *
     */
    @Column(value = Columns.NAME)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Env [name=" + name + "]";
    }

}
