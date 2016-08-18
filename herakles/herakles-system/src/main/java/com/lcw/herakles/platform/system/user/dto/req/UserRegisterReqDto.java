package com.lcw.herakles.platform.system.user.dto.req;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.lcw.herakles.platform.common.annotation.Comment;
import com.lcw.herakles.platform.common.dto.BaseMaintainableDto;
import com.lcw.herakles.platform.system.user.validation.NickNameExistenceCheck;

/**
 * 注册请求dto
 * 
 * @author chenwulou
 *
 */
@Comment(value = "用户注册请求")
public class UserRegisterReqDto extends BaseMaintainableDto {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Length(min = 4)
    @NickNameExistenceCheck
    @Comment(value = "用户名")
    private String nickName;

    @NotEmpty
    @Length(min = 4)
    @Comment(value = "密码")
    private String password;

    @NotEmpty
    @Length(min = 4)
    @Comment(value = "重复密码")
    private String passwordConfirm;

    @Comment(value = "邮箱")
    private String email;

    @NickNameExistenceCheck
    @Comment(value = "手机号")
    private String mobile;

    @NotEmpty
    @Comment(value = "验证码")
    private String captcha;

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
