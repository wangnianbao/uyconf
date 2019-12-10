package com.broada.uyconf.web.service.config.utils;

import com.broada.uyconf.core.common.constants.Constants;
import com.broada.uyconf.core.common.json.ValueVo;

public class ConfigUtils {

    /**
     * @param errorMsg
     *
     * @return
     */
    public static ValueVo getErrorVo(String errorMsg) {

        ValueVo confItemVo = new ValueVo();
        confItemVo.setStatus(Constants.NOTOK);
        confItemVo.setValue("");
        confItemVo.setMessage(errorMsg);

        return confItemVo;
    }
}
