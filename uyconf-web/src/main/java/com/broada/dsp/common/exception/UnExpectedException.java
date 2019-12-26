package com.broada.dsp.common.exception;

import com.broada.dsp.common.constant.ErrorCode;
import com.broada.dsp.common.constant.ModuleCode;
import com.broada.dsp.common.exception.base.RuntimeGlobalException;

/**
 * @author wnb
 * 14-6-24
 */
public class UnExpectedException extends RuntimeGlobalException {

    private static final long serialVersionUID = 1L;

    public UnExpectedException(String exceptionMessage) {
        super(ErrorCode.DAO_ERROR, exceptionMessage);
    }

    @Override
    public ModuleCode getModuleCode() {
        return ModuleCode.EXCEPTION;
    }

    public UnExpectedException(String exceptionMessage, Throwable throwable) {

        super(ErrorCode.UN_EXPECTED, exceptionMessage, throwable);
    }

}
