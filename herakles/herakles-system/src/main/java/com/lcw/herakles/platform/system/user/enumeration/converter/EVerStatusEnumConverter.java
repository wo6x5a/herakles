package com.lcw.herakles.platform.system.user.enumeration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.system.user.enumeration.EVerStatus;

/**
 * @author chenwulou
 *
 */
@Converter
public class EVerStatusEnumConverter extends DefaultAttributeConverter<EVerStatus>
		implements AttributeConverter<EVerStatus, String> {

}
