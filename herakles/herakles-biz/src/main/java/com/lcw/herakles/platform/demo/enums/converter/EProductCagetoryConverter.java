package com.lcw.herakles.platform.demo.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DBAttributeConverter;
import com.lcw.herakles.platform.demo.enums.EProductCagetory;

/**
 * Class Name: EProductCagetoryConverter Description: TODO
 * 
 * @author chenwulou
 *
 */
@Converter
public class EProductCagetoryConverter extends DBAttributeConverter<EProductCagetory>
		implements AttributeConverter<EProductCagetory, Integer> {

}
