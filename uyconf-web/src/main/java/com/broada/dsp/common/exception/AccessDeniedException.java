package com.broada.dsp.common.exception;

import com.broada.dsp.common.constant.ErrorCode;
import com.broada.dsp.common.constant.ModuleCode;
import com.broada.dsp.common.exception.base.RuntimeGlobalException;

/**
 * @author wnb
 * @Description: the method is not accessible to current user
 */
public class AccessDeniedException extends RuntimeGlobalException {

    private static final long serialVersionUID = 1L;

    public AccessDeniedException(String exceptionMessage) {
        super(ErrorCode.GLOBAL_ERROR, exceptionMessage);
    }

    @Override
    public ModuleCode getModuleCode() {
        return ModuleCode.OTHER;
    }

}
