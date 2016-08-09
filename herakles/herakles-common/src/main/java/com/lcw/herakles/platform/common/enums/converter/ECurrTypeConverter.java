package com.lcw.herakles.platform.common.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.common.enums.ECurrType;

@Converter
public class ECurrTypeConverter extends DefaultAttributeConverter<ECurrType>
		implements AttributeConverter<ECurrType, String> {

}
