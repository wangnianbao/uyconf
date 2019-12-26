package com.broada.dsp.common.exception.base;

import com.broada.dsp.common.constant.ErrorCode;

/**
 * 所有Exception异常类的基类
 *
 * @author wnb
 *
 */
public abstract class DspException extends Exception implements GlobalExceptionAware {

    /**
     *
     */
    private static final long serialVersionUID = 3700791594685854374L;
    protected String exceptionMessage;
    protected ErrorCode errorCode;

    public DspException() {
        super();
    }

    public DspException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * 获取异常编码
     *
     * @return
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * 获取异常消息
     *
     * @return
     */
    public String getErrorMessage() {

        return exceptionMessage;
    }

}
