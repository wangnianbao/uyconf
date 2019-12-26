package com.broada.dsp.common.constraint.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.broada.dsp.common.constraint.PageOrderConstraint;

/**
 * @author wnb
 *
 */
public class PageOrderValidator implements ConstraintValidator<PageOrderConstraint, String> {

    public static final String ASC = "asc";
    public static final String DESC = "desc";

    @Override
    public void initialize(PageOrderConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value.equals(ASC) || value.equals(DESC) || value.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
