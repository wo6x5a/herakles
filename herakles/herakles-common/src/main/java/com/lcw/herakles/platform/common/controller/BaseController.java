
package com.lcw.herakles.platform.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.lcw.herakles.platform.common.constant.ApplicationConstant;
import com.lcw.herakles.platform.common.constant.MessageConsts;
import com.lcw.herakles.platform.common.enums.DBEnum;
import com.lcw.herakles.platform.common.enums.EnumOption;
import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.common.util.MessageUtil;

/**
 * Class Name: BaseController
 * Description: all controller should extend this base class
 * 
 * @author chenwulou
 * 
 */

public abstract class BaseController implements MessageConsts{

    /**
     * Description: get all static options from a enum object for display.
     * 
     * @param enumClass
     * @return
     */

    @Autowired
    private LocalValidatorFactoryBean validator;

    protected void validate(final Object validatedObj, final Class<?>[] groups) {
        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(validatedObj, groups);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(ApplicationConstant.MANUAL_VALIDATE, constraintViolations);
        }
    }

	@SuppressWarnings("rawtypes")
	protected List<EnumOption> getStaticOptions(Class<? extends Enum> enumClass) {
		return this.getStaticOptions(enumClass, true);
	}

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected List<EnumOption> getStaticOptions(Class<? extends Enum> enumClass, boolean containBlankOption) {
        List<Enum> enumList = EnumHelper.inspectConstants(enumClass, containBlankOption);
        List<EnumOption> options = new ArrayList<EnumOption>();
        for (Enum e : enumList) {
            options.add(new EnumOption(e.name(), ((DBEnum) e).getText()));
        }
        return options;
    }
    
    /**
     * get Message by messge code
     * @param code
     * @param args
     * @return
     */
    protected String getMessage(String code, Object...args){
        return MessageUtil.getMessage(code, args);
    }

}
