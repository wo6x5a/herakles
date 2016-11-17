package com.lcw.herakles.platform.system.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;

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
    @ColumnMeta(length = "32", nullable = false, comment = "功能权限编号")
	private String permId;

	@Column(name = "PERM_CODE")
    @ColumnMeta(length = "32", nullable = false, comment = "功能权限代码")
	private String permCode;

	@Column(name = "PERM_NAME")
    @ColumnMeta(length = "32", nullable = false, comment = "功能权限名称")
	private String permName;

	@Column(name = "PERM_DESC")
    @ColumnMeta(length = "32", comment = "功能权限说明")
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
