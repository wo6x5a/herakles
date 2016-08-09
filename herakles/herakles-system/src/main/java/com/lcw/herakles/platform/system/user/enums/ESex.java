package com.lcw.herakles.platform.system.user.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

@JsonSerialize(using = PageEnumSerializer.class)
public enum ESex implements PageEnum {

	UNKNOW("U", "未知"),
	MALE("M", "男"), 
	FEMALE("F", "女");
	
	private String code;

    private String text;
	
	private ESex(String code, String text){
		this.code = code;
		this.text = text;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void setCode(String code) {
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
