package com.broada.uyconf.client.core.processor.impl;

import java.util.List;

import com.broada.uyconf.client.common.update.IUyconfUpdate;
import com.broada.uyconf.client.store.UyconfStoreProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wnb
 *
 */
public class UyconfCoreProcessUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfCoreProcessUtils.class);

    /**
     * 调用此配置影响的回调函数
     */
    public static void callOneConf(UyconfStoreProcessor uyconfStoreProcessor,
                                   String key) throws Exception {

        List<IUyconfUpdate> iUyconfUpdates = uyconfStoreProcessor.getUpdateCallbackList(key);

        //
        // 获取回调函数列表
        //

        // CALL
        for (IUyconfUpdate iUyconfUpdate : iUyconfUpdates) {

            if (iUyconfUpdate != null) {

                LOGGER.info("start to call " + iUyconfUpdate.getClass());

                // set defined
                try {

                    iUyconfUpdate.reload();

                } catch (Exception e) {

                    LOGGER.error(e.toString(), e);
                }
            }
        }
    }

}
