package com.lcw.herakles.platform.common.monitor;

import org.aopalliance.intercept.MethodInvocation;

import lombok.extern.slf4j.Slf4j;
import net.bull.javamelody.MonitoringSpringInterceptor;


/**
* Class Name: MonitoringInterceptor
* Description: for performance monitoring purpose
* @author chenwulou
*
*/

@Slf4j
public class MonitoringInterceptor extends MonitoringSpringInterceptor {

    /**
     * 
     */
    private static final long serialVersionUID = -5550296692688182091L;

//    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable { // NOSONAR
        long start = System.currentTimeMillis();
        try {
            return super.invoke(invocation);
        } finally {
            log.debug("[MON]{}.{}() completed in {}ms", invocation.getMethod().getDeclaringClass().getSimpleName(),
                    invocation.getMethod().getName(), System.currentTimeMillis() - start);
        }

    }

}
