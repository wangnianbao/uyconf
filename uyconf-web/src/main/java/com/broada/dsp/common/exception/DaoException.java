package com.broada.dsp.common.exception;

import com.broada.dsp.common.constant.ErrorCode;
import com.broada.dsp.common.constant.ModuleCode;
import com.broada.dsp.common.exception.base.RuntimeGlobalException;

/**
 * 数据库异常错误
 *
 * @author liaoqiqi
 * @version 2014-6-24
 */
public class DaoException extends RuntimeGlobalException {

    private static final long serialVersionUID = 1L;

    public DaoException(String exceptionMessage) {
        super(ErrorCode.DAO_ERROR, exceptionMessage);
    }

    @Override
    public ModuleCode getModuleCode() {
        return ModuleCode.EXCEPTION;
    }

    public DaoException(String exceptionMessage, Throwable throwable) {

        super(ErrorCode.DAO_ERROR, exceptionMessage, throwable);
    }
}
