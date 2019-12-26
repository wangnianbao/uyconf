package com.broada.dsp.common.exception;

import com.broada.dsp.common.constant.ErrorCode;
import com.broada.dsp.common.constant.ModuleCode;
import com.broada.dsp.common.exception.base.RuntimeGlobalException;

/**
 * 用于远程连接错误
 *
 * @author wnb
 *
 */
public class RemoteException extends RuntimeGlobalException {

    private static final long serialVersionUID = 1L;

    public RemoteException(String exceptionMessage) {
        super(ErrorCode.REMOTE_ERROR, exceptionMessage);
    }

    @Override
    public ModuleCode getModuleCode() {
        return ModuleCode.REMOTE;
    }

    public RemoteException(String exceptionMessage, Throwable throwable) {

        super(ErrorCode.REMOTE_ERROR, exceptionMessage, throwable);
    }
}
