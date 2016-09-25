package com.lcw.herakles.platform.bizlog.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lcw.herakles.platform.common.enums.DBIntEnum;
import com.lcw.herakles.platform.common.util.DBIntEnumSerializer;

/**
 * 操作
 * 
 * @author chenwulou
 *
 */
@JsonSerialize(using = DBIntEnumSerializer.class)
public enum EOptType implements DBIntEnum {

	ALL(0, "全部"), 
	INSERT(1, "插入"), 
	UPDATE(2, "修改"), 
	DELETE(3, "删除");

	EOptType(Integer code, String text) {
		this.code = code;
		this.text = text;
	}

	private Integer code;

	private String text;

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
