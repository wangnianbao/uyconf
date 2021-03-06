package com.broada.uyconf.client.config.inner;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Uyconf 自己的配置文件中的项标注
 *
 * @author wnb
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UyInnerConfigAnnotation {

    String name();

    String defaultValue() default "";
}
