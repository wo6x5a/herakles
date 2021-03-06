package com.lcw.herakles.platform.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility to access Spring context from a non-managed bean.
 * 
 * @author chenwulou
 * 
 */
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    
    /**
    * Description: get a bean from Spring context by type
    *
    * @param clz
    * @return
    */
    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }
    
    /**
    * Description: get a bean from Spring context by type and qualifier
    *
    * @param name
    * @param clz
    * @return
    */
    public static <T> T getBean(String name, Class<T> clz) {
        return applicationContext.getBean(name, clz);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        ApplicationContextUtil.applicationContext = applicationContext; // NOSONAR
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
