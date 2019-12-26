package com.broada.unbiz.common.genericdao.param;

/**
 * 封装大于等於的参数
 *
 * @author jay
 */
public class GteParam {
    Object value;

    public GteParam(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}