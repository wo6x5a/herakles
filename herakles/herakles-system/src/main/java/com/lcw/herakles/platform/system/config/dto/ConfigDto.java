package com.lcw.herakles.platform.system.config.dto;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.BaseMaintainableDto;
import com.lcw.herakles.platform.system.config.enums.ECfgType;
import com.lcw.herakles.platform.system.config.enums.EConfig;

/**
 * @author chenwulou
 *
 */
public class ConfigDto extends BaseMaintainableDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Comment("参数编号")
    private EConfig key;
	@Comment("参数值")
    private String value;
    @Comment("参数类型")
    private ECfgType cfgType;
    @Comment("参数备注")
    private String memo;

	public EConfig getKey() {
		return key;
	}

	public void setKey(EConfig key) {
		this.key = key;
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

	public ECfgType getCfgType() {
		return cfgType;
	}

	public void setCfgType(ECfgType cfgType) {
		this.cfgType = cfgType;
	}
    
}
