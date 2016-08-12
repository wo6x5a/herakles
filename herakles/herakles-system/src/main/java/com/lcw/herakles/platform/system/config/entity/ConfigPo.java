package com.lcw.herakles.platform.system.config.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.system.config.enums.ECfgType;
import com.lcw.herakles.platform.system.config.enums.converter.ECfgTypeEnumConverter;
import com.lcw.herakles.platform.system.entity.BaseMaintainablePo;

/**
 * @author chenwulou
 *
 */
@Entity
@Table(name = "GL_CONFIG")
public class ConfigPo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CFG_KEY")
	private String key;

	@Column(name = "CFG_VALUE")
	private String value;

	@Column(name = "CFG_TYPE")
	@Convert(converter = ECfgTypeEnumConverter.class)
	private ECfgType type;

	@Column(name = "MEMO")
	private String memo;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ECfgType getType() {
		return type;
	}

	public void setType(ECfgType type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
