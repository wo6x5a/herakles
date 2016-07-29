package com.lcw.herakles.platform.common.enumeration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.enumeration.EFlagType;
import com.lcw.herakles.platform.common.util.EnumHelper;

@Converter
public class EFlagTypeConverter implements AttributeConverter<EFlagType, String> {

    @Override
    public String convertToDatabaseColumn(EFlagType attribute) {
      return attribute.getCode();
    }
  
    @Override
    public EFlagType convertToEntityAttribute(String dbData) {
      return EnumHelper.translate(EFlagType.class, dbData);
    }

}
