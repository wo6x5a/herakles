
package com.lcw.herakles.platform.bizlog.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.bizlog.enums.EOptType;
import com.lcw.herakles.platform.bizlog.enums.converter.EBizLogOptTypeConverter;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BasePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;

import lombok.Getter;
import lombok.Setter;


/**
 * @author 系统业务日志
 *
 */
@Getter
@Setter
@Entity
@Table(name = "sys_biz_log")
@EntityListeners(IdInjectionEntityListener.class)
public class BizLogPo extends BasePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@ColumnMeta(length = "32", nullable = false, comment = "编号")
	private String id;

	@Column(name = "entity")
    @ColumnMeta(length = "40", nullable = false, comment = "操作实体")
	private String entity;

	@Column(name = "entity_id")
    @ColumnMeta(length = "32", nullable = false, comment = "实体编号")
	private String entityId;

	@Column(name = "opt_type")
    @ColumnMeta(length = "1", nullable = false, comment = "操作类型")
	@Convert(converter = EBizLogOptTypeConverter.class)
	private EOptType optType;

	@Column(name = "operate")
    @ColumnMeta(length = "30", nullable = false, comment = "具体操作")
	private String operate;

	@Column(name = "opt_ip")
    @ColumnMeta(length = "20", comment = "操作IP地址")
	private String optIp;

	@Column(name = "new_value")
    @ColumnMeta(length = "1200", comment = "新数据")
	private String newVaule;

	@Column(name = "old_value")
    @ColumnMeta(length = "1200", comment = "旧数据")
	private String oldVaule;

    @Column(name = "descr")
    @ColumnMeta(length = "200", comment = "备注")
	private String descr;

}
