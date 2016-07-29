package com.lcw.herakles.platform.common.monitor;

import net.bull.javamelody.MonitoringSpringInterceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
* Class Name: MonitoringInterceptor
* Description: for performance monitoring purpose
* @author chenwulou
*
*/
public class MonitoringInterceptor extends MonitoringSpringInterceptor {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable { // NOSONAR
        long start = System.currentTimeMillis();
        try {
            return super.invoke(invocation);
        } finally {
            LOGGER.debug("[MON]{}.{}() completed in {}ms", invocation.getMethod().getDeclaringClass().getSimpleName(),
                    invocation.getMethod().getName(), System.currentTimeMillis() - start);
        }

    }

}
