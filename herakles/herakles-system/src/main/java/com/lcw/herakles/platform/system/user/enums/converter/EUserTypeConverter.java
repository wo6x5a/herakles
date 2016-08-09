package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DBAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.EUserType;

@Converter
public class EUserTypeConverter extends DBAttributeConverter<EUserType>
		implements AttributeConverter<EUserType, Integer> {

}
