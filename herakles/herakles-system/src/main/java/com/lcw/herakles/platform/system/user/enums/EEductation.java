package com.lcw.herakles.platform.system.user.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.DBIntEnum;
import com.lcw.herakles.platform.common.util.DBIntEnumSerializer;

/**
 * 教育程度.
 * 
 * @author chenwulou
 *
 */
@JsonSerialize(using = DBIntEnumSerializer.class)
public enum EEductation implements DBIntEnum {
	ALL(0,"全部"),
	MASTER(1,"研究生及以上"),
	UNIVERSITY(2,"大学本科"),
	HIGH(3,"大学专科"),
	JUNIOR(4,"中专/高中"),
	PRIMARY(5,"高中以下");
	
	private Integer code;

	private String text;

	private EEductation(Integer code, String text) {
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
