package com.lcw.herakles.platform.common.exception;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcw.herakles.platform.common.constant.RegexpConsts;
import com.lcw.herakles.platform.common.constant.ResultCode;
import com.lcw.herakles.platform.common.dto.ResultDto;
import com.lcw.herakles.platform.common.dto.ResultDtoFactory;
import com.lcw.herakles.platform.common.dto.ValidationResultDto;

/**
 * 
 * @author chenwulou
 * 
 */
public abstract class AbstractExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractExceptionHandler.class);
    
    public ResultDto buildErrorDto(final Exception ex, final Object handler, final String formId){
        if(StringUtils.isEmpty(formId)){
            return ResultDtoFactory.toCommonError("未传入formId!");
        }else{
            ResultDto error = new ResultDto();
            error.setMessage("验证出错！");
            error.setData(new ArrayList<ValidationResultDto>());
            error.setCode(ResultCode.VALIDATION_ERROR);
            setValidationErrorData(ex, handler, formId, error);
            return error;
        }
    }
    
    protected abstract void setValidationErrorData(final Exception ex, final Object handler, final String formId, ResultDto error);

    /**
     * 
     * 
     * @param fieldName
     * @param beanName
     * @param rootClass
     * @param errorData
     * @param message
     * @param formId
     */
    protected void setFieldErrorMap(final String fieldName, final String beanName, Class<?> rootClass,
            List<ValidationResultDto> errorData, final String message, final String formId, boolean notManually) {
    	LOGGER.debug("setFieldErrorMap() invoked, field {} ", fieldName);
        boolean isExist = false;
        final String errorField = getErrorField(fieldName, rootClass, notManually);
        final int errorFieldsLen = errorField.length();
        if (errorFieldsLen > 0) {
            final String field = errorField.substring(0, errorFieldsLen - 1);
            for (ValidationResultDto validationResultDto : errorData) {
                if (beanName.equals(validationResultDto.getObjectName())) {
                    Map<String, Object> fieldErrorMap = validationResultDto.getFieldErrors();
                    fieldErrorMap.put(field, message);
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                ValidationResultDto validationResultDto = new ValidationResultDto();
                Map<String, Object> fieldErrorMap = new HashMap<String, Object>();
                fieldErrorMap.put(field, message);
                validationResultDto.setFieldErrors(fieldErrorMap);
                validationResultDto.setFormId(formId);
                validationResultDto.setObjectName(beanName);
                errorData.add(validationResultDto);
            }
        }
    }

    /**
     * 
     * 
     * @param fieldName
     * @param rootClz
     * @return
     */
    private String getErrorField(String fieldName, Class<?> rootClz, boolean notManually) {
        Class<?> rootClass = rootClz;
    	LOGGER.debug("getErrorField() invoked fieldName {} , rootClass {}", fieldName, rootClass.getName());
        final StringBuilder sb = new StringBuilder();
        final String[] pathInfo = fieldName.split("\\.");
        if (pathInfo != null && pathInfo.length > 0) {
            for (String part : pathInfo) {
                String parseFieldName = part;
                String arrayIndex = "";
                Pattern p = Pattern.compile(RegexpConsts.ARRARY_REGEXP);
                Matcher matcher = p.matcher(parseFieldName);
                boolean isFound = matcher.find();
                if (isFound) {
                    parseFieldName = matcher.group(1);
                    arrayIndex = matcher.group(2);
                }
                Field domainField = getFieldForAll(rootClass, parseFieldName);
                if (domainField != null) {
                    domainField.setAccessible(true);
                    sb.append(getJsonName(domainField, rootClass, notManually) + arrayIndex + ".");
                } else {
                    sb.append(parseFieldName + arrayIndex + ".");
                }
                try {
                    rootClass = getNextRootClass(rootClass, parseFieldName, isFound);
                } catch (NoSuchFieldException e) {
                    LOGGER.debug("NoSuchFieldException", e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 
     * 
     * @param rootClz
     * @param parseFieldName
     * @param matcher
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    private Class<?> getNextRootClass(Class<?> rootClz, final String parseFieldName, final boolean isFound)
            throws NoSuchFieldException, SecurityException {
        Class<?> rootClass = rootClz;
        if (rootClass != null) {
            try {
                Field parseField = rootClass.getDeclaredField(parseFieldName);
                if (isFound) {
                    rootClass = (Class<?>) (((ParameterizedType) (parseField.getGenericType()))
                            .getActualTypeArguments())[0];
                } else {
                    rootClass = parseField.getType();
                }
            } catch (NoSuchFieldException ex) {
                try {
                    final Class<?> superClass = rootClass.getSuperclass();
                    Field parseField = (superClass != null) ? superClass.getDeclaredField(parseFieldName) : null;
                    if (parseField != null) {
                        if (isFound) {
                            rootClass = (Class<?>) (((ParameterizedType) (parseField.getGenericType()))
                                    .getActualTypeArguments())[0];
                        } else {
                            rootClass = parseField.getType();
                        }
                    }
                } catch (NoSuchFieldException ex1) {
                    final Class<?> exsuperClass = rootClass.getSuperclass().getSuperclass();
                    Field parseField = (exsuperClass != null) ? exsuperClass.getDeclaredField(parseFieldName) : null;
                    if (parseField != null) {
                        if (isFound) {
                            rootClass = (Class<?>) (((ParameterizedType) (parseField.getGenericType()))
                                    .getActualTypeArguments())[0];
                        } else {
                            rootClass = parseField.getType();
                        }
                    }
                }
            }
            LOGGER.debug("Next rootClass = " + rootClass);
            return rootClass;
        } else {
            return null;
        }
    }
    
    /**
     * 
     * Description: find the field by filed name,
     * <p>
     * will find all super class with the field.
     * 
     * @param rootClz
     * @param fieldName
     * @return
     */
    private Field getFieldForAll(Class<?> rootClz, String fieldName) {
        Class<?> rootClass = rootClz;
    	LOGGER.debug("getFieldForAll() rootClass {} , fieldName {} ",  rootClass.getName(), fieldName);
        Set<Field> domainFields = new HashSet<Field>();
        String objectName = Object.class.getName();
        while (rootClass != null && !rootClass.getName().equals(objectName)) {
            domainFields.addAll(Arrays.asList(rootClass.getDeclaredFields()));
            rootClass = rootClass.getSuperclass();
        }
        for (Field domainField : domainFields) {
            if (fieldName.equals(domainField.getName())) {
                return domainField;
            }
        }
        return null;
    }

    /**
     * 
     * Description: get the dto or domain field
     * 
     * @param errorField
     * @param dtoClass
     * @return
     */
    private String getJsonName(final Field errorField, Class<?> dtoClass, boolean notManually) {
        if (notManually) {
            JsonProperty jsonProperty = errorField.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                return jsonProperty.value();
            }
            String jsonName = getJsonNameFromMethod(errorField, dtoClass);
            if (jsonName != null) {
				return jsonName;
			}
            return errorField.getName();
        }
        if (dtoClass == null) {
        	JsonProperty jsonProperty = errorField.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                return jsonProperty.value();
            }
            return errorField.getName();
		}
        Field[] dtoFields = dtoClass.getDeclaredFields();
        if (dtoFields != null && dtoFields.length > 0) {
            for (Field field : dtoFields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                final String[] fieldNames = fieldName.split("\\.");
                final String realFieldName = fieldNames[fieldNames.length - 1];
                if (realFieldName.equals(errorField.getName())) {
                    JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                    if (jsonProperty != null) {
                        return jsonProperty.value();
                    }
                    String jsonName = getJsonNameFromMethod(errorField, dtoClass);
                    if (jsonName != null) {
        				return jsonName;
        			}
                    return field.getName();
                }
            }
        }
        if (Object.class != dtoClass.getSuperclass()) {
            return getJsonName(errorField, dtoClass.getSuperclass(), notManually);
        }
        return null;
    }

	private String getJsonNameFromMethod(final Field errorField, Class<?> dtoClass) {
		/** find annotation from get/set method........ **/
		String methodName = "set" + errorField.getName().substring(0, 1).toUpperCase() + errorField.getName().substring(1);
		try {
			Method method = dtoClass.getDeclaredMethod(methodName, errorField.getType());
			method.setAccessible(true);
			JsonProperty jsonProperty2 = method.getAnnotation(JsonProperty.class);
			if (jsonProperty2 != null) {
		        String jp = jsonProperty2.value();
		        LOGGER.debug("JsonProperty from SetMethod ===== " + jp);
		        return jp;
		    }
		} catch (NoSuchMethodException e) {
			LOGGER.debug("NoSuchMethodException {}, try to get JsonProperty from super class", methodName);
			try {
				/** Get JsonProperty from super class.
				 *  It is only a simple implementation base on one assumption that super class should exist this field.
				 *  It does not process recursion.
				 *  **/
				Method method = dtoClass.getSuperclass().getDeclaredMethod(methodName, errorField.getType());
				method.setAccessible(true);
				JsonProperty jsonProperty2 = method.getAnnotation(JsonProperty.class);
				if (jsonProperty2 != null) {
		            String jp = jsonProperty2.value();
		            LOGGER.debug("JsonProperty from Super {} `s SetMethod ===== {} ", dtoClass.getSuperclass(),  jp);
		            return jp;
				}	
			} catch (Exception ex) { // NOSONAR
			    LOGGER.debug(e.getMessage());
			}
		} catch (SecurityException e) {
		    LOGGER.debug(e.getMessage());
		}
		return null;
	}
}
