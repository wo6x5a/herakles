package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.EUserStatus;

/**
 * @author chenwulou
 *
 */
@Converter
public class EUserStatusEnumConverter extends DefaultAttributeConverter<EUserStatus>
		implements AttributeConverter<EUserStatus, String> {

}
