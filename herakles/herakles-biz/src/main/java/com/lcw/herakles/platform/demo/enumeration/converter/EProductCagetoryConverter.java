package com.lcw.herakles.platform.demo.enumeration.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.lcw.herakles.platform.common.converter.DefaultAttributeConverter;
import com.lcw.herakles.platform.demo.enumeration.EProductCagetory;

/**
 * Class Name: EProductCagetoryConverter Description: TODO
 * 
 * @author chenwulou
 *
 */
@Converter
public class EProductCagetoryConverter extends DefaultAttributeConverter<EProductCagetory>
		implements AttributeConverter<EProductCagetory, String> {

}
