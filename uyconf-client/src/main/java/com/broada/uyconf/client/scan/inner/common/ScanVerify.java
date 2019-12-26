package com.broada.uyconf.client.scan.inner.common;

import com.broada.uyconf.client.common.annotations.UyconfFile;
import com.broada.uyconf.client.common.constants.SupportFileTypeEnum;
import com.broada.uyconf.client.common.update.IUyconfUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 扫描校验模块
 *
 * @author wnb
 *
 */
public class ScanVerify {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ScanVerify.class);

    /**
     * 判断回调函数实现的接口是否正确
     */
    public static boolean hasIUyconfUpdate(Class<?> uyconfUpdateServiceClass) {

        Class<?>[] interfaceClasses = uyconfUpdateServiceClass.getInterfaces();
        boolean hasInterface = false;
        for (Class<?> infClass : interfaceClasses) {
            if (infClass.equals(IUyconfUpdate.class)) {
                hasInterface = true;
            }
        }
        if (!hasInterface) {
            LOGGER.error("Your class " + uyconfUpdateServiceClass.toString() + " should implement interface: " +
                             IUyconfUpdate.class.toString());
            return false;
        }

        return true;
    }

    /**
     * 判断配置文件的类型是否正确
     */
    public static boolean isUyconfFileTypeRight(UyconfFile uyconfFile) {

        String fileName = uyconfFile.filename();

        SupportFileTypeEnum supportFileTypeEnum = SupportFileTypeEnum.getByFileName(fileName);

        if (supportFileTypeEnum == null) {

            LOGGER.error("now we only support this type of conf: " + uyconfFile.toString());
            return false;
        }

        return true;
    }
}
