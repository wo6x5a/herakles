package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.ESex;

public class ESexConverter extends DefaultAttributeConverter<ESex> implements
		AttributeConverter<ESex, String> {

}
