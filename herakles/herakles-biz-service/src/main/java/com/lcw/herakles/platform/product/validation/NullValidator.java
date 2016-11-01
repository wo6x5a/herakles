package com.lcw.herakles.platform.product.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.lcw.herakles.platform.common.validation.BaseValidator;


/**
* Class Name: NullValidator
* Description: TODO
* @author chenwulou
*
*/
public class NullValidator extends BaseValidator implements ConstraintValidator<NullCheck, Object> {

    @Override
    public void initialize(NullCheck check) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return true;
    }

}
