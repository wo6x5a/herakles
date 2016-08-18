
package com.lcw.herakles.platform.common.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.lcw.herakles.platform.common.dto.annotation.Domain;
import com.lcw.herakles.platform.common.dto.annotation.DomainField;
import com.lcw.herakles.platform.common.dto.annotation.OnValid;

/**
 * Class Name: ValidateInterceptor
 * <p>
 * Description: the Interceptor for service validation
 * 
 * @author chenwulou
 * 
 */
@Component
@Aspect
public class ValidateInterceptor {

    @Autowired
    private LocalValidatorFactoryBean validator;

    public ValidateInterceptor() {
        if (validator == null) {
            validator = ApplicationContextUtil.getBean(LocalValidatorFactoryBean.class);
        }
    }

    private static final String EXECUTION = "execution(* com.lcw.herakles..*.*(..,@com.lcw.herakles.platform.common.dto.annotation.OnValid (*),..))";

    /**
     * defination pointcut of service method stick <code>@Validated</code> in args.
     */
    @Pointcut(EXECUTION)
    public void findValidateAnnotation() {
    }

    /**
     * validate the business logic before executing target method.
     */
    @Before("findValidateAnnotation()")
    public void validate(final JoinPoint jp) throws Exception {
        final Signature signature = jp.getSignature();
        final Object[] args = jp.getArgs();
        final MethodSignature methodSignature = (MethodSignature) signature;
        final Method targetMethod = methodSignature.getMethod();
        Set<ConstraintViolation<?>> result = new HashSet<ConstraintViolation<?>>();
        final Annotation[][] paraAnnotations = targetMethod.getParameterAnnotations();// get the parameters annotations
        if (paraAnnotations != null && paraAnnotations.length > 0) {
            for (int i = 0; i < paraAnnotations.length; i++) {
                int paraAnnotationLength = paraAnnotations[i].length;// current parameter annotation length
                if (paraAnnotationLength == 0) { // no annotation on current parameter
                    continue;
                }
                for (int j = 0; j < paraAnnotationLength; j++) {
                    if (paraAnnotations[i][j] instanceof OnValid) {
                        OnValid validated = (OnValid) (paraAnnotations[i][j]);
                        Class<?>[] groups = (validated.value());
                        Object validatedObj = args[i];
                        Domain domainAnnotation = validatedObj.getClass().getAnnotation(Domain.class);
                        Class<?>[] domainClassList = null;
                        if (domainAnnotation != null) {
                            domainClassList = domainAnnotation.types();
                        }
                        if (domainClassList == null || domainClassList.length == 0) {
                            executeValidate(validatedObj, groups, result);
                        } else {
                            List<Object> validateDomainList = mapDtoToDomains(validatedObj, domainClassList);
                            boolean firstValidate = domainAnnotation.firstValidate();
                            boolean strict = domainAnnotation.strict();
                            executeListValidate(validateDomainList, validatedObj, groups, result, firstValidate, strict);
                        }
                        break;
                    }
                }
            }
        }
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
    }

    /**
     * 
     * Description: execute the validate and set into result set.
     * 
     * @param validatedObj
     * @param groups
     * @param result
     */
    private void executeValidate(final Object validatedObj, final Class<?>[] groups,
            final Set<ConstraintViolation<?>> result) {
        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(validatedObj, groups);
        if (!constraintViolations.isEmpty()) {
            result.addAll(constraintViolations);
        }
    }

    /**
     * 
     * Description: execute the batch validate and set into result set.
     * 
     * @param validatedObj
     * @param groups
     * @param result
     * @param firstValidate
     */
    private void executeListValidate(final List<Object> validateList, final Object validatedObj,
            final Class<?>[] groups, final Set<ConstraintViolation<?>> result, final boolean firstValidate,
            final boolean strict) {
        if (firstValidate) {
            for (Object object : validateList) {
                executeValidate(object, groups, result);
            }
            executeValidate(validatedObj, strict ? groups : new Class<?>[] {}, result);
        } else {
            executeValidate(validatedObj, strict ? groups : new Class<?>[] {}, result);
            for (Object object : validateList) {
                executeValidate(object, groups, result);
            }
        }
    }

    /**
     * 
     * Description: split the dto into several domain objects, the dto field will be mapped into the domain objects
     * fields.
     * 
     * @param validatedObj
     * @param domainClassList
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private List<Object> mapDtoToDomains(Object validatedObj, Class<?>[] domainClassList) throws Exception {
        List<Object> doaminObjectList = null;
        if (domainClassList != null && domainClassList.length > 0) {
            doaminObjectList = new ArrayList<Object>();
            for (Class<?> domainClass : domainClassList) {
                doaminObjectList.add(domainClass.newInstance());
            }
            mapFields(validatedObj, doaminObjectList);
        }
        return doaminObjectList;
    }

    /**
     * 
     * Description: TODO
     * 
     * @param validatedObj
     * @param doaminObjectList
     */
    private void mapFields(final Object validatedObj, final List<Object> doaminObjectList) throws Exception {
        Field[] validateObjFields = validatedObj.getClass().getDeclaredFields();
        Field.setAccessible(validateObjFields, true);
        if (validateObjFields != null && validateObjFields.length > 0) {
            for (Field valField : validateObjFields) {
                final String fieldName = valField.getName();
                final Object fieldValue = valField.get(validatedObj);// field value
                final DomainField fieldAnnotation = valField.getAnnotation(DomainField.class); // DomainField annotation
                Class<?> rootClass = null;
                String domainFieldName = null;
                if (fieldAnnotation != null) {
                    rootClass = fieldAnnotation.type(); // domain class
                    domainFieldName = fieldAnnotation.field();
                }
                mapFieldIntoDomain(fieldName, fieldValue, rootClass, domainFieldName, doaminObjectList);
            }
        }
    }

    /**
     * 
     * Description: TODO
     * 
     * @param fieldName
     * @param fieldValue
     * @param rootClass
     * @param domainFieldName
     * @param doaminObjectList
     */
    private void mapFieldIntoDomain(final String fieldName, final Object fieldValue, Class<?> rootClass,
            final String domainFieldName, final List<Object> doaminObjectList) throws Exception {
        // domain class exist and field no recursion
        if (rootClass != null && rootClass != String.class && !domainFieldName.contains(".")) {
            Object domainObjct = getDomainObject(doaminObjectList, rootClass);
            if (domainObjct == null) {
                throw new ClassNotFoundException("can not find the domain class " + rootClass + " in domain list "
                        + doaminObjectList);
            }
            setDomainField(domainFieldName, fieldValue, domainObjct);
        }
        // domain class not exist and field exist and field no recursion
        else if ((rootClass == null || rootClass == String.class) && StringUtils.isNotEmpty(domainFieldName)
                && !domainFieldName.contains(".")) {
            Object domainObjct = getDomainObject(doaminObjectList, domainFieldName);
            if (domainObjct == null) {
                throw new NoSuchFieldException("can not find the " + domainFieldName + " in domain list "
                        + doaminObjectList);
            }
            setDomainField(domainFieldName, fieldValue, domainObjct);
        }
        // domain class not exist and field not exist
        else if ((rootClass == null || rootClass == String.class) && StringUtils.isEmpty(domainFieldName)) {
            Object domainObjct = getDomainObject(doaminObjectList, fieldName);
            // if domainObjct is null, that mean is the field belong to dto
            if (domainObjct != null) {
                setDomainField(fieldName, fieldValue, domainObjct);
            }
        }
        // field recursion
        else if (StringUtils.isNotEmpty(domainFieldName) && domainFieldName.contains(".")) {
            final String[] pathInfo = domainFieldName.split("\\.");
            final String parseDomainFieldName = pathInfo[pathInfo.length - 1];
            Object parentDomainObjct = null;
            if (StringUtils.isBlank(parseDomainFieldName)) {
                throw new NoSuchFieldException("the domain field " + parseDomainFieldName + " is null in pathinfo");
            }
            // domain class exist
            if (rootClass != null && rootClass != String.class) {
                parentDomainObjct = getDomainObject(doaminObjectList, rootClass);
                if (parentDomainObjct == null) {
                    throw new ClassNotFoundException("can not find the domain class" + rootClass + " in domain list "
                            + doaminObjectList);
                }
            }
            // domain class not exist
            else if (rootClass == null || rootClass == String.class) {
                final String firstDomainFieldName = pathInfo[0];
                parentDomainObjct = getDomainObject(doaminObjectList, firstDomainFieldName);
                if (parentDomainObjct == null) {
                    throw new NoSuchFieldException("can not find the " + firstDomainFieldName + " in domain list "
                            + doaminObjectList);
                }
            }
            setRecurseFields(pathInfo, fieldValue, parentDomainObjct);
        }
    }

    /**
     * 
     * Description: set the recurse fields.
     * 
     * @param pathInfo
     * @param parentDomainObjct
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    private void setRecurseFields(final String[] pathInfo, final Object fieldValue, Object parentDomainObjct)
            throws Exception {
        final String parseDomainFieldName = pathInfo[pathInfo.length - 1];
        for (int i = 0; i < pathInfo.length - 1; i++) {
            final String recurObjectField = pathInfo[i];
            Field domainField = parentDomainObjct.getClass().getDeclaredField(recurObjectField);
            final String domainFieldPath = domainField.getType().getName();
            Object subDomainObject = Class.forName(domainFieldPath).newInstance();
            domainField.setAccessible(true);
            domainField.set(parentDomainObjct, subDomainObject);
            parentDomainObjct = subDomainObject;
        }
        setDomainField(parseDomainFieldName, fieldValue, parentDomainObjct);
    }

    /**
     * 
     * Description: TODO
     * 
     * @param domainFieldName
     * @param domainFieldValue
     * @param domainObject
     * @throws Exception
     */
    private void setDomainField(final String domainFieldName, final Object domainFieldValue, final Object domainObject)
            throws Exception {
        Field domainField = getDomainField(domainObject, domainFieldName);
        domainField.setAccessible(true);
        domainField.set(domainObject, domainFieldValue);
    }

    /**
     * 
     * Description: TODO
     * 
     * @param doaminObjectList
     * @param domainClass
     * @return
     */
    private Object getDomainObject(final List<Object> doaminObjectList, final Class<?> domainClass) {
        for (Object object : doaminObjectList) {
            Class<?> currentClass = object.getClass();
            if (currentClass == domainClass) {
                return object;
            }
        }
        return null;
    }

    /**
     * 
     * Description: TODO
     * 
     * @param doaminObjectList
     * @param fieldName
     * @return
     */
    private Object getDomainObject(final List<Object> doaminObjectList, final String fieldName) {
        boolean isFind = false;
        for (Object object : doaminObjectList) {
            Set<Field> domainFields = getDomainFields(object);
            for (Field domainField : domainFields) {
                if (domainField.getName().equals(fieldName)) {
                    isFind = true;
                    break;
                }
            }
            if (isFind) {
                return object;
            }
        }
        return null;
    }

    /**
     * 
     * Description: TODO
     * 
     * @param object
     * @return
     */
    private Set<Field> getDomainFields(Object object) {
        Set<Field> domainFields = new HashSet<Field>();
        Class<? extends Object> domainClass = object.getClass();
        String objectName = Object.class.getName();
        while (!domainClass.getName().equals(objectName)) {
            domainFields.addAll(Arrays.asList(domainClass.getDeclaredFields()));
            domainClass = domainClass.getSuperclass();
        }
        return domainFields;
    }

    /**
     * 
     * Description: TODO
     * 
     * @param object
     * @param domainFieldName
     * @return
     */
    private Field getDomainField(final Object object, final String domainFieldName) {
        Set<Field> domainFields = getDomainFields(object);
        for (Field domainField : domainFields) {
            if (domainFieldName.equals(domainField.getName())) {
                return domainField;
            }
        }
        return null;
    }
}
