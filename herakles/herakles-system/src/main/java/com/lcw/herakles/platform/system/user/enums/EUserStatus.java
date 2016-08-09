
package com.lcw.herakles.platform.system.user.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.DBEnum;
import com.lcw.herakles.platform.common.util.DBEnumSerializer;

/**
 * Class Name: EUserStatus
 * 
 * @author chenwulou
 * 
 */
@JsonSerialize(using = DBEnumSerializer.class)
public enum EUserStatus implements DBEnum {

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
