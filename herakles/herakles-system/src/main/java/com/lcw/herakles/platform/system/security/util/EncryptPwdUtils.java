package com.lcw.herakles.platform.system.security.util;

import org.apache.shiro.authc.credential.HashingPasswordService;
import org.apache.shiro.crypto.hash.format.DefaultHashFormatFactory;
import org.apache.shiro.crypto.hash.format.HashFormat;
import org.apache.shiro.crypto.hash.format.HashFormatFactory;
import org.apache.shiro.crypto.hash.format.ParsableHashFormat;

import com.lcw.herakles.platform.common.util.ApplicationContextUtil;
import com.lcw.herakles.platform.system.security.authc.LegacyPasswordMatchException;

/**
 * @author chenwulou
 *
 */
public class EncryptPwdUtils {

	private static HashingPasswordService getService() {
		return ApplicationContextUtil.getBean(HashingPasswordService.class);
	}

	/**
	 * 加密密码.
	 * 
	 * @param password
	 * @return
	 */
	public static String encryptPassword(String password) {
		if (!isPasswordPlainText(password)) {
			return password;
		}
		return getService().encryptPassword(password);
	}

	/**
	 * 密码是否加密.
	 * 
	 * @param password
	 * @return
	 */
	public static boolean isPasswordPlainText(String password) {
		HashFormatFactory hashFormatFactory = new DefaultHashFormatFactory();
		HashFormat discoveredFormat = hashFormatFactory.getInstance(password);
		if (discoveredFormat != null && discoveredFormat instanceof ParsableHashFormat) {
			return false;
		}
		return true;
	}

	/**
	 * 校验密码.
	 * 
	 * @param submitPwd
	 * @param encryptedPwd
	 * @return
	 */
	public static boolean isValidPassword(String submitPwd, String encryptedPwd) {
		try {
			return getService().passwordsMatch(submitPwd.trim(), encryptedPwd);
		} catch (LegacyPasswordMatchException ex) {
			return true;
		}
	}

}
