package com.lcw.herakles.platform.common.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.util.DBEnumSerializer;

@JsonSerialize(using = DBEnumSerializer.class)
public enum EFlagType implements DBEnum {
  
    ALL(0,"全部"),
    YES(1,"是"),
    NO(2,"否"),
    UNKNOW(3,"未知");

	private Integer code;

	private String text;

	private EFlagType(Integer code, String text) {
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
