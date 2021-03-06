package com.broada.dsp.common.exception.base;

import java.io.Serializable;

import com.broada.dsp.common.constant.ErrorCode;
import com.broada.dsp.common.constant.ModuleCode;

/**
 * @author wnb
 *
 */
public interface Codeable extends Serializable {

    /**
     * 获取异常编码
     *
     * @return
     */
    ErrorCode getErrorCode();

    /**
     * 获取异常消息
     *
     * @return
     */
    String getErrorMessage();

    /**
     * 获取Module代码
     *
     * @return
     */
    ModuleCode getModuleCode();

}
