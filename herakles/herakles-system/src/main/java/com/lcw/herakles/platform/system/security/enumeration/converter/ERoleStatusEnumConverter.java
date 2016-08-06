package com.lcw.herakles.platform.system.security.enumeration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.util.EnumHelper;
import com.lcw.herakles.platform.system.security.enumeration.ERoleStatus;

/**
 * @author chenwulou
 *
 */
@Converter
public class ERoleStatusEnumConverter implements AttributeConverter<ERoleStatus, String> {

    @Override
    public String convertToDatabaseColumn(ERoleStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ERoleStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ERoleStatus.class, dbData);
    }

}
