package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.EJobType;

public class EJobTypeConverter extends DefaultAttributeConverter<EJobType> 
	implements AttributeConverter<EJobType, String>{

}
