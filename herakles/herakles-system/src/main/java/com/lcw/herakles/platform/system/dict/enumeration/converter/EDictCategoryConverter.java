package com.lcw.herakles.platform.system.dict.enumeration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.system.dict.enumeration.EDictCategory;

@Converter
public class EDictCategoryConverter implements AttributeConverter<EDictCategory, String> {

    @Override
    public String convertToDatabaseColumn(EDictCategory attribute) {
      return attribute.getCode();
    }
  
    @Override
    public EDictCategory convertToEntityAttribute(String dbData) {
      return EnumHelper.translate(EDictCategory.class, dbData);
    }

}
