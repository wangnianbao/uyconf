package com.broada.unbiz.common.genericdao.param;

/**
 * 封装小于等於的参数
 *
 * @author jay
 */
public class LteParam {
    Object value;

    public LteParam(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}