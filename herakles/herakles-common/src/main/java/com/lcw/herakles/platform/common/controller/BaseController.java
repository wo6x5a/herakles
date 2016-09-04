
package com.lcw.herakles.platform.common.controller;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.google.common.collect.Lists;
import com.lcw.herakles.platform.common.constant.ApplicationConstant;
import com.lcw.herakles.platform.common.constant.MessageConsts;
import com.lcw.herakles.platform.common.enums.DBIntEnum;
import com.lcw.herakles.platform.common.enums.DBStrEnum;
import com.lcw.herakles.platform.common.enums.EnumOption;
import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.common.util.MessageUtil;

/**
 * Class Name: BaseController Description: all controller should extend this
 * base class
 * 
 * @author chenwulou
 * 
 */
public abstract class BaseController implements MessageConsts {

	@Autowired
	private LocalValidatorFactoryBean validator;

	protected void validate(final Object validatedObj, final Class<?>[] groups) {
		final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(validatedObj, groups);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(ApplicationConstant.MANUAL_VALIDATE, constraintViolations);
		}
	}

	/**
	 * 
	 * @param enumClass
	 * @param containBlankOption
	 *            是否包含第一项.
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<EnumOption> getStaticOptions(Class<? extends Enum> enumClass, boolean containBlankOption) {
		List<? extends Enum> enumList = EnumHelper.inspectConstants(enumClass, containBlankOption);
		List<EnumOption> options = Lists.newArrayList();
		for (Enum em : enumList) {
			if (em instanceof DBIntEnum) {
				options.add(new EnumOption(em.name(), ((DBIntEnum) em).getText()));
			} else if (em instanceof DBStrEnum) {
				options.add(new EnumOption(em.name(), ((DBStrEnum) em).getText()));
			}
		}
		return options;
	}

	/**
	 * get Message by messge code
	 * 
	 * @param code
	 * @param args
	 * @return
	 */
	protected String getMessage(String code, Object... args) {
		return MessageUtil.getMessage(code, args);
	}

}
