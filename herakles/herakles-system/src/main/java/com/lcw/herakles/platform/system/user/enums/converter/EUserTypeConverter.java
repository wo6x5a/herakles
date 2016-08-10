package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DBIntAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.EUserType;

@Converter
public class EUserTypeConverter extends DBIntAttributeConverter<EUserType>
		implements AttributeConverter<EUserType, Integer> {

}
