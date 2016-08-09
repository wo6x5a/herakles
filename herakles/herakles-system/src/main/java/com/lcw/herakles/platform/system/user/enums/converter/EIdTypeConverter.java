package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.EIdType;

public class EIdTypeConverter extends DefaultAttributeConverter<EIdType> implements
		AttributeConverter<EIdType, String> {

}
