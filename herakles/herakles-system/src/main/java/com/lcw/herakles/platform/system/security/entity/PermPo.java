package com.lcw.herakles.platform.system.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.system.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.system.entity.id.IdInjectionEntityListener;

@Entity
@Table(name = "SYS_PERM")
@EntityListeners(IdInjectionEntityListener.class)
public class PermPo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PERM_ID")
	private String permId;

	@Column(name = "PERM_CODE")
	private String permCode;

	@Column(name = "PERM_NAME")
	private String permName;

	@Column(name = "PERM_DESC")
	private String permDesc;

	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}

	public String getPermCode() {
		return permCode;
	}

	public void setPermCode(String permCode) {
		this.permCode = permCode;
	}

	public String getPermName() {
		return permName;
	}

	public void setPermName(String permName) {
		this.permName = permName;
	}

	public String getPermDesc() {
		return permDesc;
	}

	public void setPermDesc(String permDesc) {
		this.permDesc = permDesc;
	}

}
