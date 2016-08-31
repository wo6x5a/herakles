
package com.lcw.herakles.platform.system.user.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lcw.herakles.platform.common.enums.DBIntEnum;
import com.lcw.herakles.platform.common.util.DBIntEnumSerializer;

/**
 * 用户状态.
 * 
 * @author chenwulou
 * 
 */
@JsonSerialize(using = DBIntEnumSerializer.class)
public enum EUserStatus implements DBIntEnum {

	NULL(0, ""), 
	ACTIVE(1, "Active"), 
	INACTIVE(2, "Inactive"), 
	CANCELLED(3, "Cancelled");

	private Integer code;

	private String text;

	EUserStatus(Integer code, String text) {
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
