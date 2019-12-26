package com.broada.uyconf.client.store.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.broada.uyconf.client.common.annotations.UyconfFile;
import com.broada.uyconf.client.common.annotations.UyconfFileItem;
import com.broada.uyconf.client.common.annotations.UyconfItem;
import com.broada.uyconf.client.config.UyClientConfig;
import com.broada.uyconf.client.store.UyconfStoreProcessor;
import com.broada.uyconf.client.support.utils.MethodUtils;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.store.UyconfStoreProcessorFactory;

/**
 * 配置拦截
 *
 * @author wnb
 * 14-6-11
 */
@Aspect
public class UyconfAspectJ {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfAspectJ.class);

    @Pointcut(value = "execution(public * *(..))")
    public void anyPublicMethod() {
    }

    /**
     * 获取配置文件数据, 只有开启uyconf远程才会进行切面
     *
     * @throws Throwable
     */
    @Around("anyPublicMethod() && @annotation(uyconfFileItem)")
    public Object decideAccess(ProceedingJoinPoint pjp, UyconfFileItem uyconfFileItem) throws Throwable {

        if (UyClientConfig.getInstance().ENABLE_UYCONF) {

            MethodSignature ms = (MethodSignature) pjp.getSignature();
            Method method = ms.getMethod();

            //
            // 文件名
            //
            Class<?> cls = method.getDeclaringClass();
            UyconfFile uyconfFile = cls.getAnnotation(UyconfFile.class);

            //
            // Field名
            //
            Field field = MethodUtils.getFieldFromMethod(method, cls.getDeclaredFields(), UyConfigTypeEnum.FILE);
            if (field != null) {

                //
                // 请求仓库配置数据
                //
                UyconfStoreProcessor uyconfStoreProcessor =
                        UyconfStoreProcessorFactory.getUyconfStoreFileProcessor();
                Object ret = uyconfStoreProcessor.getConfig(uyconfFile.filename(), uyconfFileItem.name());
                if (ret != null) {
                    LOGGER.debug("using uyconf store value: " + uyconfFile.filename() + " ("
                            + uyconfFileItem.name() +
                            " , " + ret + ")");
                    return ret;
                }
            }
        }

        Object rtnOb;

        try {
            // 返回原值
            rtnOb = pjp.proceed();
        } catch (Throwable t) {
            LOGGER.info(t.getMessage());
            throw t;
        }

        return rtnOb;
    }

    /**
     * 获取配置项数据, 只有开启uyconf远程才会进行切面
     *
     * @throws Throwable
     */
    @Around("anyPublicMethod() && @annotation(uyconfItem)")
    public Object decideAccess(ProceedingJoinPoint pjp, UyconfItem uyconfItem) throws Throwable {

        if (UyClientConfig.getInstance().ENABLE_UYCONF) {
            //
            // 请求仓库配置数据
            //
            UyconfStoreProcessor uyconfStoreProcessor = UyconfStoreProcessorFactory.getUyconfStoreItemProcessor();
            Object ret = uyconfStoreProcessor.getConfig(null, uyconfItem.key());
            if (ret != null) {
                LOGGER.debug("using uyconf store value: (" + uyconfItem.key() + " , " + ret + ")");
                return ret;
            }
        }

        Object rtnOb;

        try {
            // 返回原值
            rtnOb = pjp.proceed();
        } catch (Throwable t) {
            LOGGER.info(t.getMessage());
            throw t;
        }

        return rtnOb;
    }
}
