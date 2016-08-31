package com.lcw.herakles.platform.system.user.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lcw.herakles.platform.common.enums.DBIntEnum;
import com.lcw.herakles.platform.common.util.DBIntEnumSerializer;

/**
 * 证件类型.
 * 
 * @author chenwulou
 *
 */
@JsonSerialize(using = DBIntEnumSerializer.class)
public enum EIdType implements DBIntEnum {

	ALL(0, "全部"), 
	IDCARD(1, "身份证");

	private Integer code;

	private String text;

	private EIdType(Integer code, String text) {
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
