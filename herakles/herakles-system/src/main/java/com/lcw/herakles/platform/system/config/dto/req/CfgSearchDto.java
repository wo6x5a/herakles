package com.lcw.herakles.platform.system.config.dto.req;

import java.util.List;

import com.lcw.herakles.platform.common.dto.datatable.DataTablesRequestDto;
import com.lcw.herakles.platform.system.config.enums.ECfgType;

/**
 * 参数查询dto
 * 
 * @author chenwulou
 *
 */
public class CfgSearchDto extends DataTablesRequestDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ECfgType> cfgTypeList;

	private String keyword;

	public List<ECfgType> getCfgTypeList() {
		return cfgTypeList;
	}

	public void setCfgTypeList(List<ECfgType> cfgTypeList) {
		this.cfgTypeList = cfgTypeList;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
