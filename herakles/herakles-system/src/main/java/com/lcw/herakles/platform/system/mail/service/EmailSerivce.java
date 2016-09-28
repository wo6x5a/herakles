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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.lcw.herakles.platform.common.constant.ApplicationConstant;
import com.lcw.herakles.platform.common.enums.EErrorCode;
import com.lcw.herakles.platform.common.exception.BizServiceException;
import com.lcw.herakles.platform.system.security.util.EndecryptUtils;

/**
 * @author chenwulou
 *
 */
@SuppressWarnings("deprecation")
public class EmailSerivce {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailSerivce.class);

    private static final String EMAIL_AES_KEY = "IHqLKGEYHRxLPCTbCtZfbA==";

	@Autowired
	private VelocityEngine velocityEngine;

	private String hostName;

	private String userName;

	private String password;

	private String from;

	private String smtpPort;

	public void sendSimpleEmail(String subject, String message, String... to) {
		try {
			Email email = new SimpleEmail();
			initEmail(email);
			email.addTo(to);
			email.setSubject(subject);
			email.setMsg(message);
			email.send();
		} catch (Exception e) {
			LOGGER.error("failed to send simple email, {}", e);
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
	public void sendHtmlEmail(String subject, String templateName, Map<String, Object> model, String... to) {
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
			LOGGER.error("failed to send html email, {}", e);
			throw new BizServiceException(EErrorCode.COMM_ERROR_HINTS, "邮件发送失败");
		}
	}

	private String getMessage(String templateName, Map<String, Object> model) {
		String templateLocation = "email/" + templateName + ".vm";
		String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation,
				ApplicationConstant.UTF_8, model);
		return result;
	}

	private void initEmail(Email email) {
		email.setCharset(ApplicationConstant.UTF_8);
		email.setHostName(hostName);
		email.setSmtpPort(Integer.valueOf(smtpPort));
		email.setAuthenticator(new DefaultAuthenticator(userName, password));
		email.setSSLOnConnect(false);
		try {
			email.setFrom(from);
		} catch (EmailException e) {
			LOGGER.error("failed to send html email, {}", e);
			throw new BizServiceException(EErrorCode.COMM_ERROR_HINTS, "邮件发送失败");
		}
	}

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
