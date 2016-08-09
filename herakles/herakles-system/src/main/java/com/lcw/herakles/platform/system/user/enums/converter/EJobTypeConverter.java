package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.converter.DBAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.EJobType;

public class EJobTypeConverter extends DBAttributeConverter<EJobType> implements AttributeConverter<EJobType, Integer> {

}
