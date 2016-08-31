package com.lcw.herakles.platform.system.user.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lcw.herakles.platform.common.enums.DBIntEnum;
import com.lcw.herakles.platform.common.util.DBIntEnumSerializer;

/**
 * 用户类型.
 * 
 * @author chenwulou
 *
 */
@JsonSerialize(using = DBIntEnumSerializer.class)
public enum EUserType implements DBIntEnum {

	ALL(0, "全部"), 
	PERSON(1, "个人");

	private Integer code;
	private String text;

	private EUserType(Integer code, String text) {
		this.code = code;
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
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

}
