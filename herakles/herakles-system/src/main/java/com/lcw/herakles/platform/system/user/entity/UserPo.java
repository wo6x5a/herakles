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

import com.lcw.herakles.platform.common.entity.BaseMaintainablePo;
import com.lcw.herakles.platform.common.entity.id.IdInjectionEntityListener;
import com.lcw.herakles.platform.system.user.enumeration.EUserStatus;
import com.lcw.herakles.platform.system.user.enumeration.EUserType;
import com.lcw.herakles.platform.system.user.enumeration.converter.EUserStatusEnumConverter;
import com.lcw.herakles.platform.system.user.enumeration.converter.EUserTypeConverter;

/**
 * @author chenwulou
 *
 */
@Entity
@Table(name = "SYS_USER")
@EntityListeners(IdInjectionEntityListener.class)
public class UserPo extends BaseMaintainablePo {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "SNAME")
    private String sname;

    @Column(name = "PASSWORD")
    private String password;

    @Convert(converter = EUserStatusEnumConverter.class)
    @Column(name = "STATUS")
    private EUserStatus status;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE")
    private String mobile;

    @Convert(converter = EUserTypeConverter.class)
    @Column(name = "USER_TYPE")
    private EUserType userType;

    @Column(name = "REGION")
    private String region;

    @Column(name = "ICON_FILE_ID")
    private String iconFileId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_LOGIN_TS")
    private Date lastLoginTs;

    @Column(name = "LOGIN_FAIL_CT")
    private Long loginFailCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_LOGIN_FAIL_TS")
    private Date lastLoginFailTs;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public EUserType getUserType() {
        return userType;
    }

    public void setUserType(EUserType userType) {
        this.userType = userType;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIconFileId() {
        return iconFileId;
    }

    public void setIconFileId(String iconFileId) {
        this.iconFileId = iconFileId;
    }

    public Date getLastLoginTs() {
        return lastLoginTs;
    }

    public void setLastLoginTs(Date lastLoginTs) {
        this.lastLoginTs = lastLoginTs;
    }

    public Long getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(Long loginFailCount) {
        this.loginFailCount = loginFailCount;
    }

    public Date getLastLoginFailTs() {
        return lastLoginFailTs;
    }

    public void setLastLoginFailTs(Date lastLoginFailTs) {
        this.lastLoginFailTs = lastLoginFailTs;
    }

}
