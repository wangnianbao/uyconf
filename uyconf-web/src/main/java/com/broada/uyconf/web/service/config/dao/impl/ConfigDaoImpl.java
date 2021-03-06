package com.broada.uyconf.web.service.config.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import com.broada.uyconf.web.common.Constants;
import com.broada.uyconf.web.service.config.bo.Config;
import com.broada.uyconf.web.service.config.dao.ConfigDao;
import com.broada.dsp.common.constant.DataFormatConstants;
import com.broada.dsp.common.dao.AbstractDao;
import com.broada.dsp.common.dao.Columns;
import com.broada.dsp.common.form.RequestListBase.Page;
import com.broada.dsp.common.utils.DaoUtils;
import com.broada.ub.common.db.DaoPage;
import com.broada.ub.common.db.DaoPageResult;
import com.broada.unbiz.common.genericdao.operator.Match;
import com.broada.unbiz.common.genericdao.operator.Modify;
import com.broada.unbiz.common.genericdao.operator.Order;
import com.github.knightliao.apollo.utils.time.DateUtils;

/**
 * @author wnb
 *
 */
@Service
public class ConfigDaoImpl extends AbstractDao<Long, Config> implements ConfigDao {

    /**
     *
     */
    @Override
    public Config getByParameter(Long appId, Long envId, String version, String key,
                                 UyConfigTypeEnum uyConfigTypeEnum) {

        return findOne(new Match(Columns.APP_ID, appId), new Match(Columns.ENV_ID, envId),
                new Match(Columns.VERSION, version), new Match(Columns.TYPE, uyConfigTypeEnum.getType()),
                new Match(Columns.NAME, key), new Match(Columns.STATUS, Constants.STATUS_NORMAL));
    }

    /**
     *
     */
    @Override
    public List<Config> getConfByAppEnv(Long appId, Long envId) {
        if (envId == null) {
            return find(new Match(Columns.APP_ID, appId), new Match(Columns.STATUS, Constants.STATUS_NORMAL));
        } else {

            return find(new Match(Columns.APP_ID, appId), new Match(Columns.ENV_ID, envId),
                    new Match(Columns.STATUS, Constants.STATUS_NORMAL));

        }
    }

    /**
     *
     */
    @Override
    public DaoPageResult<Config> getConfigList(Long appId, Long envId, String version, Page page) {

        DaoPage daoPage = DaoUtils.daoPageAdapter(page);
        List<Match> matchs = new ArrayList<Match>();

        matchs.add(new Match(Columns.APP_ID, appId));

        matchs.add(new Match(Columns.ENV_ID, envId));

        matchs.add(new Match(Columns.VERSION, version));

        matchs.add(new Match(Columns.STATUS, Constants.STATUS_NORMAL));

        return page2(matchs, daoPage);
    }

    /**
     *
     */
    @Override
    public List<Config> getConfigList(Long appId, Long envId, String version, Boolean hasValue) {

        List<Match> matchs = new ArrayList<Match>();
        matchs.add(new Match(Columns.APP_ID, appId));
        matchs.add(new Match(Columns.ENV_ID, envId));
        matchs.add(new Match(Columns.VERSION, version));
        matchs.add(new Match(Columns.STATUS, Constants.STATUS_NORMAL));
        if (hasValue) {
            return find(matchs, new ArrayList<Order>());
        } else {
            return findColumns(matchs, new String[] {Columns.CONFIG_ID, Columns.TYPE, Columns.NAME, Columns.CREATE_TIME
                    , Columns.UPDATE_TIME, Columns.STATUS, Columns.APP_ID, Columns.ENV_ID, Columns.VERSION});
        }
    }

    /**
     * @param configId
     */
    @Override
    public void deleteItem(Long configId) {
        String curTime = DateUtils.format(new Date(), DataFormatConstants.COMMON_TIME_FORMAT);
        List<Modify> modifyList = new ArrayList<Modify>();
        modifyList.add(modify(Columns.STATUS, Constants.STATUS_DELETE));
        modifyList.add(modify(Columns.UPDATE_TIME, curTime));

        update(modifyList, match(Columns.CONFIG_ID, configId));
    }

    /**
     *
     */
    @Override
    public void updateValue(Long configId, String value) {

        // 时间
        String curTime = DateUtils.format(new Date(), DataFormatConstants.COMMON_TIME_FORMAT);

        List<Modify> modifyList = new ArrayList<Modify>();
        modifyList.add(modify(Columns.VALUE, value));
        modifyList.add(modify(Columns.UPDATE_TIME, curTime));

        update(modifyList, match(Columns.CONFIG_ID, configId));
    }

    @Override
    public String getValue(Long configId) {
        Config config = get(configId);
        return config.getValue();
    }
}
