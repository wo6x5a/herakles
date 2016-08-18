package com.lcw.herakles.platform.system.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
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
public class MobileExistenceValidator extends BaseValidator
        implements ConstraintValidator<NickNameExistenceCheck, String> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityContext securityContext;

    @Override
    public void initialize(NickNameExistenceCheck check) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 因为reqDto可能是新增和修改,为了修改的时候不会报重复的错.两种处理方案:1.reqDto 定义group. 2.在这里判断是否是修改.这里我是用了2.这种方案.
        UserDto userDto = securityContext.getCurrentUser();
        String loginMobile = null == userDto ? "" : userDto.getMobile();
        boolean isValid = StringUtils.equals(loginMobile, value)
                || null == userRepository.findByMobile(value);
        return isValid;
    }

}
