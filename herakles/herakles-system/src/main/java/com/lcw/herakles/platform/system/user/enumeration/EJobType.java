package com.lcw.herakles.platform.system.user.enumeration;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enumeration.PageEnum;
import com.lcw.herakles.platform.common.util.PageEnumSerializer;

@JsonSerialize(using = PageEnumSerializer.class)
public enum EJobType implements PageEnum{
	
	SERVANT("01","公务员"),
	IT("02","IT"),
	CLERK("03","文员"),
	FINANCE("04","财务"),
	TEACHER("05","教师"),
	DOCTOR("06","医务"),
	MERCHANT("07","商人"),
	FARMER("08","农民"),
	LAWYER("09","律师"),
	WAITER("10","服务员"),
	WORKER("11","工人"),
	SOLDIER("12","军人"),
	OTHER("13","其他");
	
	private String code;
	
	private String text;

	private EJobType(String code, String text) {
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
