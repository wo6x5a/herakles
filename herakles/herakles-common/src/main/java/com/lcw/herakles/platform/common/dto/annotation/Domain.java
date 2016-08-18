
package com.lcw.herakles.platform.common.dto.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Domain {

    /**
     * 
     * Description: the domain class
     * 
     * @return
     */
    Class<?>[] types() default {};

    /**
     * 
     * Description: TODO
     * 
     * @return
     */
    boolean firstValidate() default true;

    /**
     * 
     * Description: if true, then the <code>groups</code> will be impact.
     * 
     * @return
     */
    boolean strict() default false;
}
