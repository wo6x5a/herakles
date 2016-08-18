
package com.lcw.herakles.platform.common.dto.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DomainField {

    /**
     * 
     * Description: the domain type.
     * 
     * @return
     */
    Class<?> type() default String.class;

    /**
     * 
     * Description: the domain filed name
     * 
     * @return
     */
    String field() default "";
}
