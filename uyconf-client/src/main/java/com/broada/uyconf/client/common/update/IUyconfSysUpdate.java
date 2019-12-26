package com.broada.uyconf.client.common.update;

import com.broada.uyconf.client.core.processor.UyconfCoreProcessor;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;

/**
 * 当发生 配置更新 时 此回调会首先被调用
 *
 * @author wnb
 *
 */
public interface IUyconfSysUpdate {

    /**
     * @param uyconfCoreProcessor    处理算子
     * @param uyConfigTypeEnum 配置类型
     * @param keyName           配置KEY
     *
     * @throws Exception
     */
    void reload(UyconfCoreProcessor uyconfCoreProcessor, UyConfigTypeEnum uyConfigTypeEnum, String keyName)
            throws Exception;
}
