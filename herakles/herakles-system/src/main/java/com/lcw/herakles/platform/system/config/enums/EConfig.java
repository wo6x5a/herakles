package com.lcw.herakles.platform.system.config.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

@JsonSerialize(using = PageEnumSerializer.class)
public enum EConfig implements PageEnum {
	
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
