package com.lcw.herakles.platform.common.exception;


/**
 * Class Name: DisplayableError
 * Description: TODO
 * @author chenwulou
 *
 */
public interface DisplayableError {
    
    /**
    * Description: get the code of the error
    *
    * @return
    */
    String getErrorCode();

    
    /**
    * Description: get the text message of the error
    *
    * @return
    */
    String getDisplayMsg();

    
    /**
    * Description: get additional information to display for the error
    *
    * @return
    */
    Object[] getArgs();

    
    /**
    * Description: whether this is a business exception
    *
    * @return
    */
    boolean isBizError();
}
