package com.lcw.herakles.platform.system.config.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.DBStrEnum;
import com.lcw.herakles.platform.common.util.DBStrEnumSerializer;

@JsonSerialize(using = DBStrEnumSerializer.class)
public enum EConfig implements DBStrEnum {
	
	/**
	 * 系统资源：1-2000 
	 */
	GL001000("GL001000", "客户标题"),
	GL001001("GL001001", "客户全名称"),
	;
	
	EConfig(String code, String text) {
		this.code = code;
		this.text = text;
	}

	private String code;

	private String text;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
