
package com.lcw.herakles.platform.demo.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.DBEnum;
import com.lcw.herakles.platform.common.util.DBEnumSerializer;

/**
 * Class Name: EProductCagetory
 * 
 * @author chenwulou
 * 
 */
@JsonSerialize(using = DBEnumSerializer.class)
public enum EProductCagetory implements DBEnum {

	ALL(0,"All"),
	BIRDS(1, "Birds"), 
	FISH(2, "Fish"), 
	DOGS(3, "Dogs"), 
	REPTILES(4, "Reptiles"), 
	CATS(5, "Cats");

	private Integer code;

	private String text;

	EProductCagetory(Integer code, String text) {
		this.code = code;
		this.text = text;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

}
