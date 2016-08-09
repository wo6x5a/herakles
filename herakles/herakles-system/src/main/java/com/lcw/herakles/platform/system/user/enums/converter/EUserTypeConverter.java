package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.system.user.enums.EUserType;

@Converter
public class EUserTypeConverter implements AttributeConverter<EUserType, String> {

    @Override
    public String convertToDatabaseColumn(EUserType attribute) {
      return attribute.getCode();
    }
  
    @Override
    public EUserType convertToEntityAttribute(String dbData) {
      return EnumHelper.translate(EUserType.class, dbData);
    }

}
