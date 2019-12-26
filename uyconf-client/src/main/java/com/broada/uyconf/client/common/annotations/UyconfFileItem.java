package com.broada.uyconf.client.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式的配置文件中的ITEM
 *
 * @author wnb
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UyconfFileItem {

    /**
     * 配置文件里的KEY的名字
     */
    String name();

    /**
     * 所关联的域
     */
    String associateField() default "";
}
