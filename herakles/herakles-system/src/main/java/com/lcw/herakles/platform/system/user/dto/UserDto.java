package com.lcw.herakles.platform.system.user.dto;

import java.util.Date;

import com.lcw.herakles.platform.common.dto.BaseMaintainableDto;
import com.lcw.herakles.platform.system.security.enumeration.EUserStatus;
import com.lcw.herakles.platform.system.user.enumeration.EUserType;

public class UserDto extends BaseMaintainableDto {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String userId;

  private String nickName;

  private String sname;

  private String password;

  private EUserStatus status;

  private String email;

  private String mobile;

  private EUserType userType;

  private String region;

  private String iconFileId;

  private Date lastLoginTs;

  private Long loginFailCount;

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
