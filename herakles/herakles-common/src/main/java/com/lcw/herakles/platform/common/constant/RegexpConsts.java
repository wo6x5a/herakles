package com.lcw.herakles.platform.common.constant;

/**
 * 正则表达式.
 * 
 * @author chenwulou
 *
 */
public final class RegexpConsts {

    public static final String MIN4_REGEXP = "(.{5,25})?$";
    public static final String MIN6_REGEXP = "(.{6,})?$";
    public static final String NUMBER_REGEXP = "[0-9]*";
    public static final String ID_CARD_REGEXP = "(\\d{15}|\\d{18}|(\\d{17}(x|X)))?$";
    public static final String BANK_CARD_REGEXP = "\\d+";//"([0-9]{1,25})?$";
    public static final String MOBILE_REGEXP = "^(\\d{11})?$";
    public static final String PHONE_REGEXP = "^(\\d{1,4}(-)?\\d{6,10}(-\\d{1,4})?)?$";
    public static final String POSTCODE_REGEXP = "(\\d{6})?$";
    public static final String QQ_REGEXP = "(\\d{4,20})?$";
    public static final String EMAIL_REGEXP = "(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)?$";
    public static final String THOUSAND_REGEX = "[0-9]+0{3}$";
    public static final String ARRARY_REGEXP = "^([a-zA-Z0-9_]+)(\\[[0-9]+\\])$";
    public static final String USER_NAME_REGEXP = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";

}
