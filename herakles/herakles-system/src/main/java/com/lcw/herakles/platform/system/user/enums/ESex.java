package com.lcw.herakles.platform.system.user.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.DBEnum;
import com.lcw.herakles.platform.common.util.DBEnumSerializer;

/**
 * 性别
 * 
 * @author chenwulou
 *
 */
@JsonSerialize(using = DBEnumSerializer.class)
public enum ESex implements DBEnum {

	UNKNOW(0, "未知"),
	MALE(1, "男"), 
	FEMALE(2, "女");
	
	private Integer code;

    private String text;
	
	private ESex(Integer code, String text){
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
