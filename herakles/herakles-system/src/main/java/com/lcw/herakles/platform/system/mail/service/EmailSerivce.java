package com.lcw.herakles.platform.system.mail.service;

import java.net.URL;
import java.util.Map;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.lcw.herakles.platform.common.constant.ApplicationConsts;
import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.exception.BizServiceException;
import com.lcw.herakles.platform.system.mail.dto.MailInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenwulou
 *
 */
@SuppressWarnings("deprecation")
@Slf4j
public class EmailSerivce {

    @Autowired
    private VelocityEngine velocityEngine;

    private MailInfo mailInfo;

    public void setMailInfo(MailInfo mailInfo) {
        this.mailInfo = mailInfo;
    }

    /**
     * send simple email
     * 
     * @param subject
     * @param message
     * @param to
     */
    public void sendSimpleEmail(String subject, String message, String... to) {
        try {
            Email email = new SimpleEmail();
            initEmail(email);
            email.addTo(to);
            email.setSubject(subject);
            email.setMsg(message);
            email.send();
        } catch (Exception e) {
            log.error("failed to send simple email:", e);
            throw new BizServiceException(EErrorCode.COMM_ERROR_HINTS, "邮件发送失败");
        }
    }

    /**
     * Sending HTML formatted email.
     * <p>
     * put your template under resource:email/{@code templateName}.vm
     *
     * @param subject
     * @param message
     * @param to
     */
    public void sendHtmlEmail(String subject, String templateName, Map<String, Object> model,
            String... to) {
        String message = getMessage(templateName, model);
        try {
            URL url = new URL("http://www.apache.org");
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            initEmail(email);
            email.addTo(to);
            email.setSubject(subject);
            email.setHtmlMsg(message);
            // set the alternative message
            email.setTextMsg("Your email client does not support HTML messages");
            email.send();
        } catch (Exception e) {
            log.error("failed to send html email:", e);
            throw new BizServiceException(EErrorCode.COMM_ERROR_HINTS, "邮件发送失败");
        }
    }

    private String getMessage(String templateName, Map<String, Object> model) {
        String templateLocation = "email/" + templateName + ".vm";
        String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                templateLocation, ApplicationConsts.UTF_8, model);
        return result;
    }

    private void initEmail(Email email) {
        email.setCharset(ApplicationConsts.UTF_8);
        email.setHostName(mailInfo.getHostName());
        email.setSmtpPort(Integer.valueOf(mailInfo.getSmtpPort()));
        email.setAuthenticator(new DefaultAuthenticator(mailInfo.getUserName(), mailInfo.getPassword()));
        // TODO true
        email.setSSLOnConnect(false);
        try {
            email.setFrom(mailInfo.getFrom());
        } catch (EmailException e) {
            log.error("failed to send html email:", e);
            throw new BizServiceException(EErrorCode.COMM_ERROR_HINTS, "邮件发送失败");
        }
    }
}
