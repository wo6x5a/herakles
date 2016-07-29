package com.lcw.herakles.platform.common.enumeration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.enumeration.ECurrType;
import com.lcw.herakles.platform.common.util.EnumHelper;

@Converter
public class ECurrTypeConverter implements AttributeConverter<ECurrType, String> {

    @Override
    public String convertToDatabaseColumn(ECurrType attribute) {
      return attribute.getCode();
    }
  
    @Override
    public ECurrType convertToEntityAttribute(String dbData) {
      return EnumHelper.translate(ECurrType.class, dbData);
    }

}
