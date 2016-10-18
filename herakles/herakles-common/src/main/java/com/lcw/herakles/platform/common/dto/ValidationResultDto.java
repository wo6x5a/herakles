package com.lcw.herakles.platform.common.dto;

import java.util.Map;

import com.lcw.herakles.platform.common.annotation.Comment;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Class Name: ValidationResultDto
 * <p>
 * Description: the validation result for form.
 * 
 * @author chenwulou
 * 
 */
@Getter
@Setter
@Comment(value = "校验返回dto")
public class ValidationResultDto {

    @Comment(value = "表单id")
    private String formId;

    @Comment(value = "对象名")
    private String objectName;

    @Comment(value = "general error")
    private Object generalError;

    @Comment(value = "field errors")
    private Map<String, Object> fieldErrors;
}
