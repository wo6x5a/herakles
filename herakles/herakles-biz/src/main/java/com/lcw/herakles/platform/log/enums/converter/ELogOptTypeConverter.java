package com.lcw.herakles.platform.log.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DBIntAttributeConverter;
import com.lcw.herakles.platform.log.enums.EOptType;

@Converter
public class ELogOptTypeConverter extends DBIntAttributeConverter<EOptType>
		implements AttributeConverter<EOptType, Integer> {
}
