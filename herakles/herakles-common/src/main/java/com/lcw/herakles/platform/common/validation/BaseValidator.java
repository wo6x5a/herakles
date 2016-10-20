
package com.lcw.herakles.platform.common.validation;

import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

/**
 * Class Name: BaseValidator Description: TODO
 * 
 * @author chenwulou
 * 
 */
public class BaseValidator {

    /**
     * 
     * Description: TODO
     * 
     * @param context
     * @param fieldName
     * @param messageTemplate
     */
    @SuppressWarnings("deprecation")
    protected void bindNode(final ConstraintValidatorContext context, final String fieldName, String messageTemplate) {
        String template = messageTemplate;
        if (StringUtils.isBlank(messageTemplate)) {
            template = context.getDefaultConstraintMessageTemplate();
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template).addNode(fieldName).addConstraintViolation();
    }
}
