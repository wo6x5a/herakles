package com.lcw.herakles.platform.common.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtil.class);

	/**
	 * MD5 加密
	 * 
	 */
	public static String getMD5(String reqStr) {
		return SecurityUtil.getSecurity(reqStr, "MD5");
	}

	/**
	 * SHA-1加密
	 * 
	 */
	public static String getSHA1(String reqStr) {
		return SecurityUtil.getSecurity(reqStr, "SHA-1");
	}

	private static String getSecurity(String str, String securityType) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(securityType);
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (Exception e) {
			LOGGER.error("密文转换异常！{}", e.getMessage());
		}
		return SecurityUtil.byte2hex(messageDigest.digest());
	}

	private static String byte2hex(byte[] byteArray) {
		StringBuffer resp = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				resp.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				resp.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return resp.toString();

	}

	public static void main(String[] args) {
		System.out.println(SecurityUtil.getMD5("123456"));
		System.out.println(SecurityUtil.getSHA1("123456"));
	}
}
