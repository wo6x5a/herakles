package com.lcw.herakles.platform.bizlog.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.bizlog.enums.EOptType;
import com.lcw.herakles.platform.common.converter.DBIntAttributeConverter;

@Converter
public class EBizLogOptTypeConverter extends DBIntAttributeConverter<EOptType>
		implements AttributeConverter<EOptType, Integer> {
}
