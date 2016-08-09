package com.lcw.herakles.platform.system.user.enums;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.lcw.herakles.platform.common.enums.DBEnum;
import com.lcw.herakles.platform.common.util.DBEnumSerializer;

@JsonSerialize(using = DBEnumSerializer.class)
public enum EJobType implements DBEnum{
	ALL(0,"全部"),
	SERVANT(1,"公务员"),
	IT(2,"IT"),
	CLERK(3,"文员"),
	FINANCE(4,"财务"),
	TEACHER(5,"教师"),
	DOCTOR(6,"医务"),
	MERCHANT(7,"商人"),
	FARMER(8,"农民"),
	LAWYER(9,"律师"),
	WAITER(10,"服务员"),
	WORKER(11,"工人"),
	SOLDIER(12,"军人"),
	OTHER(13,"其他");
	
	private Integer code;
	
	private String text;

	private EJobType(Integer code, String text) {
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
