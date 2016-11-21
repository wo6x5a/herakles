package com.lcw.herakles.platform.system.security.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sys_role")
@EntityListeners(IdInjectionEntityListener.class)
public class RolePo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "role_id")
    @ColumnMeta(length = "32", nullable = false, comment = "角色编号")
	private String roleId;

	@Column(name = "role_code")
    @ColumnMeta(length = "20", nullable = false, uniqueKey = "role_code", comment = "角色代码")
	private String roleCode;

	@Column(name = "role_name")
    @ColumnMeta(length = "60", nullable = false, comment = "角色名称")
	private String roleName;

	@Column(name = "role_descr")
    @ColumnMeta(length = "200", comment = "角色备注")
	private String roleDescr;

	@OneToMany
	@JoinTable(name = "sys_role_perm", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "perm_id"))
	private List<PermPo> permPoList;

}
