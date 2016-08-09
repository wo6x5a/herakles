package com.lcw.herakles.platform.log.dto.req;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.datatable.DataTablesRequestDto;
import com.lcw.herakles.platform.log.enums.EOptType;

/**
 * 日志查询dto
 * 
 * @author chenwulou
 *
 */
public class LogQueryDto extends DataTablesRequestDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Comment("编号")
	private String id;

	@Comment("操作实体")
	private String entity;

	@Comment("操作类型")
	private EOptType type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public EOptType getType() {
		return type;
	}

	public void setType(EOptType type) {
		this.type = type;
	}

}
