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

import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.system.security.entity.id.UserRolePk;
import com.lcw.herakles.platform.system.security.enumeration.ERoleStatus;
import com.lcw.herakles.platform.system.security.enumeration.converter.ERoleStatusEnumConverter;
import com.lcw.herakles.platform.system.user.entity.UserPo;

@Entity
@Table(name = "SYS_USER_ROLE")
@IdClass(UserRolePk.class)
public class UserRolePo extends BaseMaintainablePo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Id
    @Column(name = "ROLE_ID")
    private String roleId;

    @Convert(converter = ERoleStatusEnumConverter.class)
    @Column(name = "STATUS")
    private ERoleStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private UserPo userPo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)
    private RolePo rolePo;

}
