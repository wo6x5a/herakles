package com.lcw.herakles.platform.system.user.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.DBIntEnum;
import com.lcw.herakles.platform.common.util.DBIntEnumSerializer;

/**
 * 验证码状态.
 * 
 * @author chenwulou
 *
 */
@JsonSerialize(using = DBIntEnumSerializer.class)
public enum EVerStatus implements DBIntEnum {

	ALL(0, "全部"), 
	UNUSE(1, "未使用"), 
	USED(2, "已使用");

	private Integer code;

	private String text;

	private EVerStatus(Integer code, String text) {
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
