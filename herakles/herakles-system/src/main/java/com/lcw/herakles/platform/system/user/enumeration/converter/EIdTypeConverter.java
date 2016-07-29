package com.lcw.herakles.platform.system.user.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.system.user.enumeration.EIdType;

public class EIdTypeConverter extends DefaultAttributeConverter<EIdType> implements
		AttributeConverter<EIdType, String> {

}
