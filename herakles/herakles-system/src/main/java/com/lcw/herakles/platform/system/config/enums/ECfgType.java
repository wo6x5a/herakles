package com.lcw.herakles.platform.system.config.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lcw.herakles.platform.common.enums.DBIntEnum;
import com.lcw.herakles.platform.common.util.DBIntEnumSerializer;

@JsonSerialize(using = DBIntEnumSerializer.class)
public enum ECfgType implements DBIntEnum {

	SYS(0, "系统参数"),
	CFG(1, "配置参数"),
	BIZ(2, "业务参数"),
	;
	
	ECfgType(Integer code, String text) {
		this.code = code;
		this.text = text;
	}

	private Integer code;

	private String text;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
