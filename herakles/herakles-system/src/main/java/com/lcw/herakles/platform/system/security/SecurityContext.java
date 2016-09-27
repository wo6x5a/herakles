
package com.lcw.herakles.platform.system.security;

import org.springframework.stereotype.Component;

import com.lcw.herakles.platform.system.security.BaseSecurityContext;
import com.lcw.herakles.platform.system.security.SecurityContext;

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
