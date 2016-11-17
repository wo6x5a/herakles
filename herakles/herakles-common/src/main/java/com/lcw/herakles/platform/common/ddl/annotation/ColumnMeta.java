/**
 * 
 */
package com.lcw.herakles.platform.common.ddl.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;



/**
 * @author chenwulou
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface ColumnMeta {

	String length() default "";
	
	boolean nullable() default true;
	
	String defaultValue() default "NULL";
	
	String comment() default "";
	
	String key() default "";
	
	String uniqueKey() default "";
	
	int order() default 0; 
}
