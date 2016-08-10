package com.lcw.herakles.platform.system.config.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DBIntAttributeConverter;
import com.lcw.herakles.platform.system.config.enums.ECfgType;

@Converter
public class ECfgTypeEnumConverter extends DBIntAttributeConverter<ECfgType>
		implements AttributeConverter<ECfgType, Integer> {

}
