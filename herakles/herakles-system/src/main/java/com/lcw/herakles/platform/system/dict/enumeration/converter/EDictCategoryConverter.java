package com.lcw.herakles.platform.system.dict.enumeration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.system.dict.enumeration.EDictCategory;

@Converter
public class EDictCategoryConverter extends DefaultAttributeConverter<EDictCategory>
		implements AttributeConverter<EDictCategory, String> {

}
