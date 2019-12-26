package com.broada.dsp.common.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.broada.dsp.common.constraint.validation.PasswordValidator;

/**
 * @author wnb
 * 14-1-14
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordConstraint {

    String message() default "password.not.right";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
