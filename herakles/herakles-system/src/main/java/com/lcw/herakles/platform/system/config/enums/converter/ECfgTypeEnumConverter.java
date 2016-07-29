package com.lcw.herakles.platform.system.config.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.system.config.enums.ECfgType;

@Converter
public class ECfgTypeEnumConverter extends DefaultAttributeConverter<ECfgType>
		implements AttributeConverter<ECfgType, String> {

}
