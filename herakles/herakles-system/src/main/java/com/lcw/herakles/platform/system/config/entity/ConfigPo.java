package com.lcw.herakles.platform.system.config.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.system.config.enums.ECfgType;
import com.lcw.herakles.platform.system.config.enums.converter.ECfgTypeEnumConverter;

/**
 * @author chenwulou
 *
 */
@Entity
@Table(name = "GL_CONFIG")
@Comment(value = "参数表")
public class ConfigPo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CFG_KEY")
    @ColumnMeta(length = "32", nullable = false, comment = "参数编号")
	private String key;

	@Column(name = "CFG_VALUE")
    @ColumnMeta(length = "40", nullable = false, comment = "参数值")
	private String value;

	@Column(name = "CFG_TYPE")
	@Convert(converter = ECfgTypeEnumConverter.class)
    @ColumnMeta(length = "1", nullable = false, comment = "参数类型")
	private ECfgType type;

	@Column(name = "MEMO")
    @ColumnMeta(length = "100", nullable = false, comment = "参数备注")
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
