package com.lcw.herakles.platform.system.user.enumeration;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enumeration.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

@JsonSerialize(using = PageEnumSerializer.class)
public enum EEductation implements PageEnum {
	MASTER("01","研究生及以上"),
	UNIVERSITY("02","大学本科"),
	HIGH("03","大学专科"),
	JUNIOR("04","中专/高中"),
	PRIMARY("05","高中以下");
	
	private String code;

	private String text;

	private EEductation(String code, String text) {
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
