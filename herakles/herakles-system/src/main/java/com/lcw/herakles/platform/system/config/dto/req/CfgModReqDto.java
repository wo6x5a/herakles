package com.lcw.herakles.platform.system.config.dto.req;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.lcw.herakles.platform.common.annotation.Comment;

public class CfgModReqDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "{common.error.field.empty}")
	private String key;
	
	@Comment("交易密码")
	@NotEmpty(message = "{common.error.field.empty}")
	private String trxPwd;

	private String value;
	
	private String memo;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTrxPwd() {
		return trxPwd;
	}

	public void setTrxPwd(String trxPwd) {
		this.trxPwd = trxPwd;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
