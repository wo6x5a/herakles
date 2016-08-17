package com.lcw.herakles.platform.system.user.dto.req;

import com.lcw.herakles.platform.common.dto.BaseMaintainableDto;

/**
 * 注册请求dto
 * 
 * @author chenwulou
 *
 */
public class UserRegisterReqDto extends BaseMaintainableDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String nickName;

    private String password;

    private String passwordConfirm;

    private String email;

    private String mobile;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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

}
