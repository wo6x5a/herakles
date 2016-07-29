package com.lcw.herakles.platform.system.security.dto.req;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;


/**
* Class Name: SignInDto
* Description: TODO
* @author chenwulou
*
*/
public class SignInDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotEmpty
    @Length(min = 4)
    private String userName;
    @NotEmpty
    @Length(min = 4)
    private String password;
    @NotEmpty
    private String captcha;
    private boolean rememberMe;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
