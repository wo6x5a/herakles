
package com.lcw.herakles.platform.bizlog.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.bizlog.enums.EOptType;
import com.lcw.herakles.platform.bizlog.enums.converter.EBizLogOptTypeConverter;
import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BasePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;

@Entity
@Table(name = "SYS_BIZ_LOG")
@EntityListeners(IdInjectionEntityListener.class)
public class BizLogPo extends BasePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@ColumnMeta(length = "32", nullable = false, comment = "编号")
	private String id;

	@Column(name = "ENTITY")
    @ColumnMeta(length = "40", nullable = false, comment = "操作实体")
	private String entity;

	@Column(name = "ENTITY_ID")
    @ColumnMeta(length = "32", nullable = false, comment = "实体编号")
	private String entityId;

	@Column(name = "OPT_TYPE")
    @ColumnMeta(length = "1", nullable = false, comment = "操作类型")
	@Convert(converter = EBizLogOptTypeConverter.class)
	private EOptType optType;

	@Column(name = "OPERATE")
    @ColumnMeta(length = "30", nullable = false, comment = "具体操作")
	private String operate;

	@Column(name = "OPT_IP")
    @ColumnMeta(length = "20", comment = "操作IP地址")
	private String optIp;

	@Column(name = "NEW_VALUE")
    @ColumnMeta(length = "1200", comment = "新数据")
	private String newVaule;

	@Column(name = "OLD_VALUE")
    @ColumnMeta(length = "1200", comment = "旧数据")
	private String oldVaule;

	@Comment("备注")
    @ColumnMeta(length = "200", comment = "备注")
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
