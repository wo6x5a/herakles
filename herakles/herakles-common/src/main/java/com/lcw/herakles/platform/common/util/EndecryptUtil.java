package com.lcw.herakles.platform.common.util;

import java.security.Key;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.Md5Hash;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.lcw.herakles.platform.common.constant.ApplicationConsts;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenwulou
 *
 */

@Slf4j
public class EndecryptUtil {

    /**
     * base64进制加密
     * 
     * @param password
     * @return
     */
    public static String encrytBase64(String plainText) {
        if (StringUtils.isBlank(plainText)) {
            log.error("EndecryptUtils.encrytBase64(), plainText不能为空");
            throw new IllegalArgumentException(String.valueOf("plainText不能为空"));
        }
        byte[] bytes = plainText.getBytes();
        return Base64.encodeToString(bytes);
    }

    /**
     * base64进制解密
     * 
     * @param cipherText
     * @return
     */
    public static String decryptBase64(String cipherText) {
        if (StringUtils.isBlank(cipherText)) {
            log.error("EndecryptUtils.decryptBase64(), cipherText不能为空");
            throw new IllegalArgumentException(String.valueOf("cipherText不能为空"));
        }
        return Base64.decodeToString(cipherText);
    }

    /**
     * 16进制加密
     * 
     * @param password
     * @return
     */
    public static String encrytHex(String plainText) {
        if (StringUtils.isBlank(plainText)) {
            log.error("EndecryptUtils.encrytHex(), plainText不能为空");
            throw new IllegalArgumentException(String.valueOf("plainText不能为空"));
        }
        byte[] bytes = plainText.getBytes();
        return Hex.encodeToString(bytes);
    }

    /**
     * 16进制解密
     * 
     * @param cipherText
     * @return
     */
    public static String decryptHex(String cipherText) {
        if (StringUtils.isBlank(cipherText)) {
            log.error("EndecryptUtils.decryptHex(), cipherText不能为空");
            throw new IllegalArgumentException(String.valueOf("cipherText不能为空"));
        }
        Preconditions.checkArgument(!Strings.isNullOrEmpty(cipherText), "消息摘要不能为空");
        return new String(Hex.decode(cipherText));
    }

    /**
     * aes加密
     * 
     * @param plainText
     * @param key
     * @return
     */
    public static String encryptAes(String plainText, String key) {
        if (StringUtils.isBlank(plainText)) {
            log.error("EndecryptUtils.encryptAes(), plainText不能为空");
            throw new IllegalArgumentException(String.valueOf("plainText不能为空"));
        }
        if (StringUtils.isBlank(key)) {
            log.error("EndecryptUtils.encryptAes(), key不能为空");
            throw new IllegalArgumentException(String.valueOf("key不能为空"));
        }
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);
        return aesCipherService.encrypt(plainText.getBytes(), Base64.decode(key)).toHex();
    }

    /**
     * aes解密
     * 
     * @param cipherText
     * @param key
     * @return
     */
    public static String decryptAes(String cipherText, String key) {
        if (StringUtils.isBlank(cipherText)) {
            log.error("EndecryptUtils.decryptAes(), cipherText不能为空");
            throw new IllegalArgumentException(String.valueOf("cipherText不能为空"));
        }
        if (StringUtils.isBlank(key)) {
            log.error("EndecryptUtils.encryptAes(), key不能为空");
            throw new IllegalArgumentException(String.valueOf("key不能为空"));
        }
        AesCipherService aes = new AesCipherService();
        aes.setKeySize(128);
        return new String(aes.decrypt(Hex.decode(cipherText), Base64.decode(key)).getBytes());
    }

    /**
     * MD5加密.
     * 
     * @param password 密码
     * @param salt 盐
     * @return
     */
    public static String encrytMD5(String plainText, String salt) {
        if (StringUtils.isBlank(plainText)) {
            log.error("EndecryptUtils.encrytMD5(), plainText不能为空");
            throw new IllegalArgumentException(String.valueOf("plainText不能为空"));
        }
        return new Md5Hash(plainText, salt, ApplicationConsts.ITERATIONS).toHex();
    }

    /**
     * 生成KEY
     * 
     * @return
     */
    public static String generateKey() {
        AesCipherService aesCipherService = new AesCipherService();
        Key key = aesCipherService.generateNewKey();
        return Base64.encodeToString(key.getEncoded());
    }

    public static void main(String[] args) {
        // String password = "admin";
        // System.out.println(encrytMD5(password, null));
        // String cipherText = encrytHex(password);
        // System.out.println(password + " hex加密之后的密文是：" + cipherText);
        // String decrptPassword = decryptHex(cipherText);
        // System.out.println(cipherText + " hex解密之后的密码是：" + decrptPassword);
        // String cipherText_base64 = encrytBase64(password);
        // System.out.println(password + " base64加密之后的密文是：" + cipherText_base64);
        // String decrptPassword_base64 = decryptBase64(cipherText_base64);
        // System.out.println(cipherText_base64 + " base64解密之后的密码是：" + decrptPassword_base64);
        // String h64 = H64.encodeToString(password.getBytes());
        // System.out.println(h64);
        // String salt = "7road";
        // String cipherText_md5 = new Md5Hash(password, salt, 4).toHex();
        // System.out.println(password + " 通过md5加密之后的密文是：" + cipherText_md5);
//         System.out.println(generateKey());
        // System.out.println("==========================================================");
        // AesCipherService aesCipherService = new AesCipherService();
        // aesCipherService.setKeySize(128);
        // Key key = aesCipherService.generateNewKey();
        // String aes_cipherText =
        // aesCipherService.encrypt(password.getBytes(), key.getEncoded()).toHex();
        // System.out.println(password + " aes加密的密文是：" + aes_cipherText);
        // System.out.println();
        // String aes_mingwen = new String(
        // aesCipherService.decrypt(Hex.decode(aes_cipherText), key.getEncoded()).getBytes());
        // System.out.println(aes_cipherText + " aes解密的明文是：" + aes_mingwen);
         System.out.println(encryptAes("123456", "5QSC8vwvBi7ohPkukgT0Bw=="));
        // System.out.println(decryptAes(
        // "949a88e84577cdb3299d15846a02ecf7b63644186747848e96ef1885406abc00444da42b77676a6df786c6c0a3579eb4835e2ac184eb361ecd725cbc1171f3f5",
        // null));
//        System.out.println(generateKey());
    }
}
