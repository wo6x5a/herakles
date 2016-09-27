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

import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;

@Entity
@Table(name = "SYS_ROLE")
@EntityListeners(IdInjectionEntityListener.class)
public class RolePo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROLE_ID")
	private String roleId;

	@Column(name = "ROLE_CODE")
	private String roleCode;

	@Column(name = "ROLE_NAME")
	private String roleName;

	@Column(name = "ROLE_DESC")
	private String roleDesc;

	@OneToMany
	@JoinTable(name = "SYS_ROLE_PERM", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PERM_ID"))
	private List<PermPo> permPoList;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public List<PermPo> getPermPoList() {
		return permPoList;
	}

	public void setPermPoList(List<PermPo> permPoList) {
		this.permPoList = permPoList;
	}

}
