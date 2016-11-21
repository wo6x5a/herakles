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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SYS_ROLE_PERM")
@IdClass(RolePermPK.class)
public class RolePermPo extends BaseMaintainablePo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "role_id")
    @ColumnMeta(length = "100", nullable = false, comment = "角色ID")
    private String roleId;

    @Id
    @Column(name = "perm_id")
    @ColumnMeta(length = "100", nullable = false, comment = "权限ID")
    private String permId;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "perm_id", insertable = false, updatable = false)
    private PermPo permPo;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private RolePo rolePo;

}
