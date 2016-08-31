package com.lcw.herakles.platform.common.exception;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.method.HandlerMethod;

import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ValidationResultDto;
import com.lcw.herakles.platform.common.util.MessageUtil;
import com.lcw.herakles.platform.common.validation.ValidateException;

/**
 * Class Name: BindingResultExceptionHandler
 * <p>
 * Description: the <code>ValidateException</code> handler<br>
 * the validation from service will be wrapped into <code>ValidateException</code>, then the handler will catch the
 * exception and return the errors into view
 * 
 * @author chenwulou
 * 
 */
@Service
public class BindingResultExceptionHandler extends AbstractExceptionHandler {
    

    /**
     * 
     * Description: set the validation data.
     * 
     * @param bindingResults
     * @param handler
     * @param formId
     * @param error
     * @param locale
     */
    @Override
    protected void setValidationErrorData(final Exception ex, final Object handler, final String formId, ResultDto error) {
        ValidateException vex = (ValidateException) ex;
        List<BindingResult> bindingResults = vex.getBindingResults(); // get those bindingResults
        @SuppressWarnings("unchecked")
        final List<ValidationResultDto> errorData = (List<ValidationResultDto>) error.getData();
        if (StringUtils.isNotEmpty(formId) && bindingResults != null && bindingResults.size() > 0
                && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // method parameter arrays
            MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
            if (methodParameters != null && methodParameters.length > 0) {
                for (BindingResult bindingResult : bindingResults) {
                    Class<?> doaminClass = bindingResult.getTarget().getClass();
                    for (MethodParameter methodParameter : methodParameters) {
                        Class<?> dtoClass = methodParameter.getParameterType();
                        if (!dtoClass.equals(doaminClass)) {
                            continue;
                        } else if (doaminClass.equals(dtoClass)) {
                            setResultDto(bindingResult, errorData, formId, false);
                        }
                    }
                }
            } else {
                for (BindingResult bindingResult : bindingResults) {
                    setResultDto(bindingResult, errorData, formId, true);
                }
            }
        }
    }

    /**
     * 
     * Description: set the result dto
     * 
     * @param locale
     * 
     * @param constraintViolation
     * @throws NoSuchFieldException
     */
    private void setResultDto(BindingResult bindingResult, List<ValidationResultDto> errorData, String formId, boolean notManually) {
        List<FieldError> fieldErros = bindingResult.getFieldErrors();
        String beanName = bindingResult.getObjectName();
        Object rootObject = bindingResult.getTarget();
        Class<?> rootClass = rootObject.getClass();
        if (fieldErros != null && fieldErros.size() > 0) {
            for (FieldError fieldError : fieldErros) {
                final String fieldName = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                final String errorCode = fieldError.getCode();
                if (StringUtils.isEmpty(message)) {
                    message = MessageUtil.getMessage(StringUtils.isNotEmpty(errorCode) ? errorCode : message);
                }
                setFieldErrorMap(fieldName, beanName, rootClass, errorData, message, formId, notManually);
            }
        }
    }
}
