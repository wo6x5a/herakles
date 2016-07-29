package com.lcw.herakles.platform.common.dto;

import java.util.Map;

/**
 * 
 * Class Name: ValidationResultDto
 * <p>
 * Description: the validation result for form.
 * 
 * @author chenwulou
 * 
 */
public class ValidationResultDto {

    private String formId;

    private String objectName;

    private Object generalError;

    private Map<String, Object> fieldErrors;

    /**
     * @return return the value of the var formId
     */

    public String getFormId() {
        return formId;
    }

    /**
     * @param formId
     *            Set formId value
     */

    public void setFormId(String formId) {
        this.formId = formId;
    }

    /**
     * @return return the value of the var objectName
     */

    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName
     *            Set objectName value
     */

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * @return return the value of the var generalError
     */

    public Object getGeneralError() {
        return generalError;
    }

    /**
     * @param generalError
     *            Set generalError value
     */

    public void setGeneralError(Object generalError) {
        this.generalError = generalError;
    }

    /**
     * @return return the value of the var fieldErrors
     */

    public Map<String, Object> getFieldErrors() {
        return fieldErrors;
    }

    /**
     * @param fieldErrors
     *            Set fieldErrors value
     */

    public void setFieldErrors(Map<String, Object> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
