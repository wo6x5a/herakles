package com.lcw.herakles.platform.system.security.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.system.security.entity.id.UserRolePk;
import com.lcw.herakles.platform.system.security.enums.ERoleStatus;
import com.lcw.herakles.platform.system.security.enums.converter.ERoleStatusEnumConverter;
import com.lcw.herakles.platform.system.user.entity.UserPo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sys_user_role")
@IdClass(UserRolePk.class)
public class UserRolePo extends BaseMaintainablePo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @ColumnMeta(length = "32", nullable = false, comment = "用户编号")
    private String userId;

    @Id
    @Column(name = "role_id")
    @ColumnMeta(length = "100", nullable = false, comment = "角色编号")
    private String roleId;

    @Convert(converter = ERoleStatusEnumConverter.class)
    @Column(name = "status")
    @ColumnMeta(length = "1", nullable = false, defaultValue = "1", comment = "0-待生效, 1-已生效，默认1-已生效")
    private ERoleStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserPo userPo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private RolePo rolePo;

}
