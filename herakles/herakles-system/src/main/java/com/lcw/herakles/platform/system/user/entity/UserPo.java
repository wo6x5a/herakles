package com.lcw.herakles.platform.system.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.system.user.enums.EUserStatus;
import com.lcw.herakles.platform.system.user.enums.EUserType;
import com.lcw.herakles.platform.system.user.enums.converter.EUserStatusEnumConverter;
import com.lcw.herakles.platform.system.user.enums.converter.EUserTypeConverter;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenwulou
 *
 */
@Getter
@Setter
@Entity
@Table(name = "sys_user")
@EntityListeners(IdInjectionEntityListener.class)
public class UserPo extends BaseMaintainablePo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    @ColumnMeta(length = "32", nullable = false, comment = "用户编号")
    private String userId;

    @Column(name = "nick_name")
    @ColumnMeta(length = "20", nullable = false, comment = "用户名")
    private String nickName;

    @Column(name = "sname")
    @ColumnMeta(length = "40", nullable = false, comment = "用户姓名")
    private String sname;

    @Column(name = "password")
    @ColumnMeta(length = "100", nullable = false, comment = "密码")
    private String password;

    @Convert(converter = EUserStatusEnumConverter.class)
    @Column(name = "status")
    @ColumnMeta(length = "2", nullable = false, comment = "状态:1 Active, 2 Inactive, 3 Cancelled")
    private EUserStatus status;

    @Column(name = "email")
    @ColumnMeta(length = "100", comment = "邮箱")
    private String email;

    @Column(name = "mobile")
    @ColumnMeta(length = "20", nullable = false, comment = "手机号")
    private String mobile;

    @Convert(converter = EUserTypeConverter.class)
    @Column(name = "user_type")
    @ColumnMeta(length = "2", nullable = false, comment = "用户类型")
    private EUserType userType;

    @Column(name = "region")
    @ColumnMeta(length = "30", comment = "所属区域")
    private String region;

    @Column(name = "icon_file_id")
    @ColumnMeta(length = "100", comment = "用户头像")
    private String iconFileId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_ts")
    @ColumnMeta(length = "6", comment = "最后登录时间")
    private Date lastLoginTs;

    @Column(name = "login_fail_ct")
    @ColumnMeta(length = "3", comment = "登录失败次数")
    private Long loginFailCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login_fail_ts")
    @ColumnMeta(length = "6", comment = "最后登陆失败时间")
    private Date lastLoginFailTs;

}
