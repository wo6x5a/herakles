package com.lcw.herakles.platform.system.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lcw.herakles.platform.common.validation.BaseValidator;
import com.lcw.herakles.platform.system.security.SecurityContext;
import com.lcw.herakles.platform.system.user.dto.UserDto;
import com.lcw.herakles.platform.system.user.repository.UserRepository;


/**
 * 
 * @author chenwulou
 *
 */
@Component
public class NickNameExistenceValidator extends BaseValidator
        implements ConstraintValidator<NickNameExistenceCheck, String> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityContext securityContext;

    @Override
    public void initialize(NickNameExistenceCheck check) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        UserDto userDto = securityContext.getCurrentUser();
        String loginNickName = null == userDto ? "" : userDto.getNickName();
        boolean isValid = StringUtils.equals(loginNickName, value)
                || null == userRepository.findByNickName(value);
        return isValid;
    }

}
