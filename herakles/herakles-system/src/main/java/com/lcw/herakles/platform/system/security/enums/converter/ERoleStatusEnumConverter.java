package com.lcw.herakles.platform.system.security.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DBStrAttributeConverter;
import com.lcw.herakles.platform.system.security.enums.ERoleStatus;

/**
 * @author chenwulou
 *
 */
@Converter
public class ERoleStatusEnumConverter extends DBStrAttributeConverter<ERoleStatus>
implements AttributeConverter<ERoleStatus, String> {

}
