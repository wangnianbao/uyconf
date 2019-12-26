package com.broada.unbiz.common.genericdao.param;

/**
 * 封装小于的参数
 *
 * @author jay
 */
public class LessThanParam {
    Object value;

    public LessThanParam(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}