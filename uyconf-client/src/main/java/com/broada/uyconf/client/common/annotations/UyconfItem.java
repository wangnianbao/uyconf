package com.broada.uyconf.client.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式的配置项
 *
 * @author wnb
 * 14-5-16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UyconfItem {

    /**
     * 配置项的KEY，不能为空
     */
    String key();

    /**
     * 所关联的域
     */
    String associateField() default "";

    /**
     * 环境,,默认为用户指定的环境
     */
    String env() default "";

    /**
     * 版本,默认为用户指定的app
     */
    String app() default "";

    /**
     * 版本,默认为用户指定的版本
     */
    String version() default "";
}
