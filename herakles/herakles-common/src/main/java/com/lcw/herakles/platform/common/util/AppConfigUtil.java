package com.lcw.herakles.platform.common.util;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * Class Name: AppConfigUtil Description: 获取应用全局配置
 * 
 * @author chenwulou
 * 
 */
public final class AppConfigUtil {

    private AppConfigUtil() {

    }

    private static final String ENVIRONMENT = "env";

    private static MessageSource messageSource;

    public static String getConfig(String key) {
        return messageSource.getMessage(key, null, Locale.ROOT);
    }

    /**
     * Whether current profile is for PROD environment.
     * 
     * @return
     */
    public static boolean isProdEnv() {
        return "PROD".equalsIgnoreCase(getConfig(ENVIRONMENT));
    }

    /**
     * @param messageSource
     *            Set messageSource value
     */
    public static void setMessageSource(MessageSource messageSource) {
        AppConfigUtil.messageSource = messageSource;
    }

}
