package com.lcw.herakles.platform.common.util.ftp.pool.dto;

import com.lcw.herakles.platform.common.util.EndecryptUtils;

/**
 * Description: ftp信息
 * 
 * @author chennwulou
 *
 */
public class FtpInfo {

    private static final String FTP_AES_KEY = "5QSC8vwvBi7ohPkukgT0Bw==";

	private String ftpHost;

	private String ftpUserName;

	private String ftpPassword;

	private int ftpPort;

	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
	    ftpHost = EndecryptUtils.decryptAes(ftpHost, FTP_AES_KEY);
		this.ftpHost = ftpHost;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
	    ftpUserName = EndecryptUtils.decryptAes(ftpUserName, FTP_AES_KEY);
		this.ftpUserName = ftpUserName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
	    ftpPassword = EndecryptUtils.decryptAes(ftpPassword, FTP_AES_KEY);
		this.ftpPassword = ftpPassword;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}

}
