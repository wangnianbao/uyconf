package com.broada.dsp.common.exception.base;

import com.broada.dsp.common.constant.ErrorCode;

/**
 * 实现 RuntimeError的基类
 *
 * @author wnb
 *
 */
public abstract class RuntimeGlobalException extends RuntimeException implements GlobalExceptionAware {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected String exceptionMessage;
    protected ErrorCode errorCode;
    protected Throwable cause;

    /**
     * @param exceptionMessage
     */
    public RuntimeGlobalException(String exceptionMessage) {
        this(ErrorCode.DEFAULT_ERROR, exceptionMessage);
    }

    /**
     * @param exceptionMessage
     */
    public RuntimeGlobalException(ErrorCode errorCode, String exceptionMessage) {
        this(errorCode, exceptionMessage, null);
    }

    /**
     * @param exceptionMessage
     * @param cause
     */
    public RuntimeGlobalException(ErrorCode errorCode, String exceptionMessage, Throwable cause) {
        this.errorCode = errorCode;
        this.exceptionMessage = exceptionMessage;
        this.cause = cause;
    }

    public Throwable getCause() {
        return cause;
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
