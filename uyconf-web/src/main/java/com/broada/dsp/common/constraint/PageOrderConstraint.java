package com.broada.dsp.common.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.broada.dsp.common.constraint.validation.PageOrderValidator;

/**
 * \
 *
 * @author wnb
 *
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PageOrderValidator.class)
public @interface PageOrderConstraint {

    String message() default "page.order.error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
