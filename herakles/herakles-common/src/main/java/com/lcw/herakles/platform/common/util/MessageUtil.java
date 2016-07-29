package com.lcw.herakles.platform.common.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;


/**
* Class Name: MessageUtil
* Description: TODO
* @author chenwulou
*
*/
public final class MessageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);

    private static MessageSource messageSource;

    private static Locale defaultLocale = Locale.SIMPLIFIED_CHINESE;

    private MessageUtil() {
        
    }

    /**
     * 
     * Description: get error message
     * 
     * @param code
     * @return
     */
    public static String getMessage(String code) {
        return getMessage(code, defaultLocale);
    }

    /**
    * Description: get error message with parameters
    *
    * @param code
    * @param args
    * @return
    */
    public static String getMessage(String code, Object... args) {
        return getMessage(code, defaultLocale, args);
    }

    
    /**
    * Description: get error message with locale and parameters
    *
    * @param code
    * @param locale
    * @param args
    * @return
    */
    public static String getMessage(String code, Locale locale, Object... args) {
        LOGGER.debug("getMessage() invoked,  Message code: " + code);
        return messageSource.getMessage(code, args, "Unknown message, code: " + code, locale);
    }

    public static void setMessageSource(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

}
