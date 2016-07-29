
package com.lcw.herakles.platform.system.security;

import org.springframework.stereotype.Component;

/**
 * Class Name: PageSecurityContext Description: TODO
 * 
 * @author chenwulou
 * 
 */
@Component
public class SecurityContext extends BaseSecurityContext {

    public static SecurityContext getInstance() {
        return new SecurityContext();
    }

}
