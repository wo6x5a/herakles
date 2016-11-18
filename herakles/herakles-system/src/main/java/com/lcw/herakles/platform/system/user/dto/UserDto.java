package com.lcw.herakles.platform.system.user.dto;

import java.util.Date;

import com.lcw.herakles.platform.common.dto.BaseMaintainableDto;
import com.lcw.herakles.platform.system.user.enums.EUserStatus;
import com.lcw.herakles.platform.system.user.enums.EUserType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenwulou
 *
 */
@Getter
@Setter
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

}
