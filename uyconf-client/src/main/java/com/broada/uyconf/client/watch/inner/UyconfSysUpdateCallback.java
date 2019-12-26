package com.broada.uyconf.client.watch.inner;

import com.broada.uyconf.client.common.update.IUyconfSysUpdate;
import com.broada.uyconf.client.core.processor.UyconfCoreProcessor;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;

/**
 * 当配置更新时，系统会自动 调用此回调函数<br/>
 * 这个函数是系统调用的，当有配置更新时，便会进行回调
 *
 * @author wnb
 * 14-5-16
 */
public class UyconfSysUpdateCallback implements IUyconfSysUpdate {

    /**
     *
     */
    @Override
    public void reload(UyconfCoreProcessor uyconfCoreMgr, UyConfigTypeEnum uyConfigTypeEnum, String keyName)
        throws Exception {

        // 更新配置数据仓库 && 调用用户的回调函数列表
        uyconfCoreMgr.updateOneConfAndCallback(keyName);
    }
}
