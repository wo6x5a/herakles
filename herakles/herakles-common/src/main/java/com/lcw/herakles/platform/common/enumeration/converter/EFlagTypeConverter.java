package com.lcw.herakles.platform.common.enumeration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.common.enumeration.EFlagType;

@Converter
public class EFlagTypeConverter extends DefaultAttributeConverter<EFlagType>
		implements AttributeConverter<EFlagType, String> {
}
