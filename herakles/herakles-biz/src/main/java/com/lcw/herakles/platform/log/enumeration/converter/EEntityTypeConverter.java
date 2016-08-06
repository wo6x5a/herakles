package com.lcw.herakles.platform.log.enumeration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.log.enumeration.EEntityType;

@Converter
public class EEntityTypeConverter extends DefaultAttributeConverter<EEntityType>
		implements AttributeConverter<EEntityType, String> {

}
