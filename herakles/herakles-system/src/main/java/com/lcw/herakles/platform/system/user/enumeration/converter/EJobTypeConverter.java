package com.lcw.herakles.platform.system.user.enumeration.converter;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.system.user.enumeration.EJobType;

public class EJobTypeConverter extends DefaultAttributeConverter<EJobType> 
	implements AttributeConverter<EJobType, String>{

}
