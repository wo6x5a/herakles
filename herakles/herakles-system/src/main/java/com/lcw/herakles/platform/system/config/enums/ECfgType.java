package com.lcw.herakles.platform.system.config.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

@JsonSerialize(using = PageEnumSerializer.class)
public enum ECfgType implements PageEnum {

	SYS("00", "系统参数"),
	CFG("10", "配置参数"),
	BIZ("20", "业务参数"),
	;
	
	ECfgType(String code, String text) {
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
