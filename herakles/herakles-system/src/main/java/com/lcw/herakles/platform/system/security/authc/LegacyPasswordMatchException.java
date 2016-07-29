	
package com.lcw.herakles.platform.system.security.authc;

/**
 * Class Name: LegacyPasswordException
 * Description: TODO
 * @author chenwulou
 *
 */
public class LegacyPasswordMatchException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    
    /**
    * LegacyPasswordMatchException Constructor
    *
    */
    public LegacyPasswordMatchException(){
        super();
    }
    
    
    /**
    * LegacyPasswordMatchException Constructor
    *
    * @param message
    */
    public LegacyPasswordMatchException(String message){
        super(message);
    }
    
}
