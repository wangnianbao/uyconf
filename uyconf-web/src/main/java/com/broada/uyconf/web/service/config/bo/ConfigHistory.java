package com.broada.uyconf.web.service.config.bo;

import com.broada.dsp.common.dao.Columns;
import com.broada.dsp.common.dao.DB;
import com.broada.unbiz.common.genericdao.annotation.Column;
import com.broada.unbiz.common.genericdao.annotation.Table;
import com.github.knightliao.apollo.db.bo.BaseObject;

import lombok.Data;

@Data
@Table(db = DB.DB_NAME, name = "config_history", keyColumn = "id")
public class ConfigHistory extends BaseObject<Long> {

    /**
     *
     */
    @Column(value = Columns.CONFIG_ID)
    private Long configId;

    /**
     *
     */
    @Column(value = "old_value")
    private String oldValue;

    /**
     *
     */
    @Column(value = "new_value")
    private String newValue;

    /**
     * 创建时间
     */
    @Column(value = Columns.CREATE_TIME)
    private String createTime;

    /**
     *
     */
    @Column(value = Columns.UPDATE_BY)
    private Long updateBy;

}
