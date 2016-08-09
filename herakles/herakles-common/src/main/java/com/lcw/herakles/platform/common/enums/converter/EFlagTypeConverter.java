package com.lcw.herakles.platform.common.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DBAttributeConverter;
import com.lcw.herakles.platform.common.enums.EFlagType;

@Converter
public class EFlagTypeConverter extends DBAttributeConverter<EFlagType>
		implements AttributeConverter<EFlagType, Integer> {
}
