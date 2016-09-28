package com.lcw.herakles.platform.system.mail.dto;

import com.lcw.herakles.platform.common.util.EndecryptUtils;

public class MailInfo {

    private static final String EMAIL_AES_KEY = "IHqLKGEYHRxLPCTbCtZfbA==";

    private String hostName;

    private String userName;

    private String password;

    private String from;

    private String smtpPort;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        hostName = EndecryptUtils.decryptAes(hostName, EMAIL_AES_KEY);
        this.hostName = hostName;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        smtpPort = EndecryptUtils.decryptAes(smtpPort, EMAIL_AES_KEY);
        this.smtpPort = smtpPort;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        userName = EndecryptUtils.decryptAes(userName, EMAIL_AES_KEY);
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = EndecryptUtils.decryptAes(password, EMAIL_AES_KEY);
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        from = EndecryptUtils.decryptAes(from, EMAIL_AES_KEY);
        this.from = from;
    }


}
