
package com.lcw.herakles.platform.log.dto;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.BaseDto;
import com.lcw.herakles.platform.log.enums.EOptType;

/**
 * @author chenwulou
 *
 */
public class LogDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Comment("编号")
	private String id;

	@Comment("操作实体")
	private String entity;

	@Comment("实体编号")
	private String entityId;

	@Comment("操作类型")
	private EOptType optType;

	@Comment("具体操作")
	private String operate;

	@Comment("操作IP地址")
	private String optIp;

	@Comment("新数据")
	private String newVaule;

	@Comment("旧数据")
	private String oldVaule;

	@Comment("备注")
	private String comment;

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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public EOptType getOptType() {
		return optType;
	}

	public void setOptType(EOptType optType) {
		this.optType = optType;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getOptIp() {
		return optIp;
	}

	public void setOptIp(String optIp) {
		this.optIp = optIp;
	}

	public String getNewVaule() {
		return newVaule;
	}

	public void setNewVaule(String newVaule) {
		this.newVaule = newVaule;
	}

	public String getOldVaule() {
		return oldVaule;
	}

	public void setOldVaule(String oldVaule) {
		this.oldVaule = oldVaule;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
