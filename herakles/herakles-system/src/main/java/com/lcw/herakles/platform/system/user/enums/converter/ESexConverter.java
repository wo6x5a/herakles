package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.converter.DBIntAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.ESex;

public class ESexConverter extends DBIntAttributeConverter<ESex> implements AttributeConverter<ESex, Integer> {

}
