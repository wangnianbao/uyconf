package com.broada.uyconf.web.service.config.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.broada.uyconf.web.service.config.bo.ConfigHistory;
import com.broada.uyconf.web.service.config.dao.ConfigHistoryDao;
import com.broada.uyconf.web.service.config.service.ConfigHistoryMgr;
import com.broada.uyconf.web.service.user.dto.Visitor;
import com.broada.dsp.common.constant.DataFormatConstants;
import com.broada.ub.common.commons.ThreadContext;
import com.github.knightliao.apollo.utils.time.DateUtils;

/**
 * Created by wnb on 15/12/25.
 */
@Service
public class ConfigHistoryMgrImpl implements ConfigHistoryMgr {

    @Autowired
    private ConfigHistoryDao configHistoryDao;

    @Override
    public void createOne(Long configId, String oldValue, String newValue) {

        ConfigHistory configHistory = new ConfigHistory();

        configHistory.setConfigId(configId);
        configHistory.setOldValue(oldValue);
        configHistory.setNewValue(newValue);

        Visitor visitor = ThreadContext.getSessionVisitor();
        if (visitor != null) {
            configHistory.setUpdateBy(visitor.getLoginUserId());
        }

        String curTime = DateUtils.format(new Date(), DataFormatConstants.COMMON_TIME_FORMAT);
        configHistory.setCreateTime(curTime);

        configHistoryDao.create(configHistory);
    }
}
