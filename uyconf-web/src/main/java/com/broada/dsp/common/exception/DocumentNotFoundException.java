package com.broada.dsp.common.exception;

/**
 * 文档不存在错误
 *
 * @author wnb
 *
 */
public class DocumentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 545280194497047918L;

    public DocumentNotFoundException(String fileName) {
        super(fileName + " file does not exist!");
    }

}
