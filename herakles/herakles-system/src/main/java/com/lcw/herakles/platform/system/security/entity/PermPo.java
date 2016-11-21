package com.lcw.herakles.platform.system.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sys_perm")
@EntityListeners(IdInjectionEntityListener.class)
public class PermPo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "perm_id")
    @ColumnMeta(length = "100", nullable = false, comment = "功能权限编号")
	private String permId;

	@Column(name = "perm_code")
    @ColumnMeta(length = "100", nullable = false, comment = "功能权限代码")
	private String permCode;

	@Column(name = "perm_name")
    @ColumnMeta(length = "100", nullable = false, comment = "功能权限名称")
	private String permName;

	@Column(name = "perm_descr")
    @ColumnMeta(length = "100", comment = "功能权限说明")
	private String permDescr;

}
