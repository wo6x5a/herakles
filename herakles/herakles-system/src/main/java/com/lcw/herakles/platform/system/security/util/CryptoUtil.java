package com.lcw.herakles.platform.system.security.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.util.ByteSource;

/**
 * Class Name: CryptoUtil Description: use AES as the symmetric crypto algorithm
 * 
 * @author chenwulou
 * 
 */
public final class CryptoUtil {

	private CryptoUtil() {

	}

	public static String encrypt(String plainText, String key) {
		AesCipherService aes = new AesCipherService();
		byte[] keyBytes = CodecSupport.toBytes(key);
		ByteSource encryptedBytes = aes.encrypt(CodecSupport.toBytes(plainText), keyBytes);
		return encryptedBytes.toString();
	}

	public static String decrypt(String cipherText, String key) {
		AesCipherService aesCs = new AesCipherService();
		byte[] keyBytes = CodecSupport.toBytes(key);
		ByteSource decryptedBytes = aesCs.decrypt(Base64.decode(CodecSupport.toBytes(cipherText)), keyBytes);
		return CodecSupport.toString(decryptedBytes.getBytes());
	}

}
