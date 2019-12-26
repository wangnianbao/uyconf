package com.broada.dsp.common.constraint.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.broada.dsp.common.constraint.PasswordConstraint;

/**
 * @author wnb
 *
 */
public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        if (value.length() <= 2) {
            return false;
        }

        return true;
    }

}
