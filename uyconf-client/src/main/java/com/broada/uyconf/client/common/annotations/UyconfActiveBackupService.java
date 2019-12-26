package com.broada.uyconf.client.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识需要进行主备切换的服务,需要指定它影响的配置数据，<br/>
 * 包括了配置文件和配置项
 *
 * @author wnb
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UyconfActiveBackupService {

    /**
     * 配置文件
     */
    Class<?>[] classes() default {};

    /**
     * 配置项
     */
    String[] itemKeys() default {};
}
