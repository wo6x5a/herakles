package com.lcw.herakles.platform.system.log.enumeration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.system.log.enumeration.ELogType;

@Converter
public class ELogTypeConverter implements AttributeConverter<ELogType, String> {

    @Override
    public String convertToDatabaseColumn(ELogType attribute) {
        return attribute.getCode();
    }

    @Override
    public ELogType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ELogType.class, dbData);
    }

}
