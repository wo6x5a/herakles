package com.lcw.herakles.platform.system.user.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.DBEnum;
import com.lcw.herakles.platform.common.util.DBEnumSerializer;

@JsonSerialize(using = DBEnumSerializer.class)
public enum EUserType implements DBEnum {

	ALL(0, "全部"), PERSON(1, "个人");

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
