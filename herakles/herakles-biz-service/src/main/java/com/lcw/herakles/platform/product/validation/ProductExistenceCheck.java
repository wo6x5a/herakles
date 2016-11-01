
package com.lcw.herakles.platform.product.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * <p>
 * Description: the user name exist check.
 * 
 * @author chenwulou
 * 
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductExistenceValidator.class)
@Documented
public @interface ProductExistenceCheck {

    /**
     * Description: the error message when validation error occurs
     *
     * @return
     */
     String message() default "{product.already.exists}";

     
     /**
     * Description: the validation groups if any
     *
     * @return
     */
     Class<?>[] groups() default {};

     
     /**
     * Description: {@link Payload} that needs to be used during validation
     *
     * @return
     */
     Class<? extends Payload>[] payload() default {};
}
