package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.converter.DBAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.EIdType;

public class EIdTypeConverter extends DBAttributeConverter<EIdType> implements AttributeConverter<EIdType, Integer> {

}
