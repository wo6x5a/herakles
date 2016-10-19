package com.lcw.herakles.platform.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author chenwulou
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NumberUtil {

    public static String toRMB(double money) {
        if (money > 9999999999999.99 || money < -9999999999999.99) {
            return "数值位数过大!";
        }
        String signStr = "";
        String tailStr = "";
        if (money < 0) {
            money = -money;
            signStr = "负";
        }
        char[] s1 = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
        char[] s4 = {'分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟', '万'};
        String str = String.valueOf(Math.round(money * 100 + 0.00001));

        for (int i = 0; i < str.length(); i++) {
            int n = str.charAt(str.length() - 1 - i) - '0';
            tailStr = s1[n] + "" + s4[i] + tailStr;
        }

        tailStr = tailStr.replaceAll("零仟", "零");
        tailStr = tailStr.replaceAll("零佰", "零");
        tailStr = tailStr.replaceAll("零拾", "零");
        tailStr = tailStr.replaceAll("零亿", "亿");
        tailStr = tailStr.replaceAll("零万", "万");
        tailStr = tailStr.replaceAll("零元", "元");
        tailStr = tailStr.replaceAll("零角", "零");
        tailStr = tailStr.replaceAll("零分", "零");

        tailStr = tailStr.replaceAll("零零", "零");
        tailStr = tailStr.replaceAll("零亿", "亿");
        tailStr = tailStr.replaceAll("零零", "零");
        tailStr = tailStr.replaceAll("零万", "万");
        tailStr = tailStr.replaceAll("零零", "零");
        tailStr = tailStr.replaceAll("零元", "元");
        tailStr = tailStr.replaceAll("亿万", "亿");

        tailStr = tailStr.replaceAll("零$", "");
        tailStr = tailStr.replaceAll("元$", "元整");
        tailStr = tailStr.replaceAll("角$", "角整");

        return signStr + tailStr;
    }
}
