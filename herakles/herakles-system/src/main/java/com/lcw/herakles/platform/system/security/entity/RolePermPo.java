package com.lcw.herakles.platform.system.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.system.security.entity.id.RolePermPK;

@Entity
@Table(name = "SYS_ROLE_PERM")
@IdClass(RolePermPK.class)
public class RolePermPo extends BaseMaintainablePo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ROLE_ID")
    @ColumnMeta(length = "32", nullable = false, comment = "角色ID")
    private String roleId;

    @Id
    @Column(name = "PERM_ID")
    @ColumnMeta(length = "32", nullable = false, comment = "权限ID")
    private String permId;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "PERM_ID", insertable = false, updatable = false)
    private PermPo permPo;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)
    private RolePo rolePo;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermId() {
        return permId;
    }

    public void setPermId(String permId) {
        this.permId = permId;
    }

    public PermPo getPermPo() {
        return permPo;
    }

    public void setPermPo(PermPo permPo) {
        this.permPo = permPo;
    }

    public RolePo getRolePo() {
        return rolePo;
    }

    public void setRolePo(RolePo rolePo) {
        this.rolePo = rolePo;
    }

}
