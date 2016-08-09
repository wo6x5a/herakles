package com.lcw.herakles.platform.system.user.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DBAttributeConverter;
import com.lcw.herakles.platform.system.user.enums.EVerStatus;

/**
 * @author chenwulou
 *
 */
@Converter
public class EVerStatusEnumConverter extends DBAttributeConverter<EVerStatus>
		implements AttributeConverter<EVerStatus, Integer> {

}
