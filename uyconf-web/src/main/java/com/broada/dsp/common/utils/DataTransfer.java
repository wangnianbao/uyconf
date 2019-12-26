package com.broada.dsp.common.utils;

/**
 * 数据转接接口
 *
 * @author wnb
 *
 */
public interface DataTransfer<ENTITYFROM, ENTITYTO> {

    /**
     * 转换规则定义
     *
     * @param inputList
     *
     * @return
     */
    public ENTITYTO transfer(ENTITYFROM input);
}
