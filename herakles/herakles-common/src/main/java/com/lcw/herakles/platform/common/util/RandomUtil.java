package com.lcw.herakles.platform.common.util;

import java.util.Random;

/**
 * random util
 * 
 * @author chenwulou
 *
 */
public class RandomUtil {

    private final static String _ALPHABETS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String _NUMBER = "0123456789";

    /**
     * 随机数字
     * 
     * @param length
     * @return
     */
    public static String getNumber(int length) {
        return random(_NUMBER, length);
    }

    /**
     * 随机字母
     * 
     * @param length
     * @return
     */
    public static String getAlphabets(int length) {
        return random(_ALPHABETS, length);
    }

    /**
     * 随机数字和字母
     * 
     * @param length
     * @return
     */
    public static String getAlnum(int length) {
        return random(_ALPHABETS + _NUMBER, length);
    }

    private static String random(String src, int length) {
        int strLen = src.length();
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(strLen);
            buf.append(src.charAt(num));
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        System.out.println(getAlnum(80));
    }
}