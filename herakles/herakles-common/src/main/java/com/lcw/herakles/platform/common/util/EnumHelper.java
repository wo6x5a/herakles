package com.lcw.herakles.platform.common.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lcw.herakles.platform.common.enums.EnumOption;
import com.lcw.herakles.platform.common.enums.PageEnum;

/**
 * 
 * @author chenwulou
 * 
 */
public final class EnumHelper implements Serializable {

	private static final long serialVersionUID = 5L;
	private static final String GET_CODE_METHOD = "getCode";
    private static final String GET_LABEL_METHOD = "getText";
	private static final Logger LOGGER = LoggerFactory.getLogger(EnumHelper.class);

	private EnumHelper() {

	}

	/**
	 * Gets an mutable list of enumerations defined in the given Enum.
	 * 
	 * @param <T>
	 *            subclass of Enum
	 * @param clazz
	 *            the class instance
	 * @return a list of objects of type T
	 */
	public static <T extends Enum<T>> List<T> inspectConstants(final Class<T> clazz) {
		return new ArrayList<T>(Arrays.asList(clazz.getEnumConstants()));
	}

    /**
     * Gets an mutable list of enumerations defined in the given Enum.
     * 
     * @param <T>
     *            subclass of Enum
     * @param clazz
     *            the class instance
     * @return a list of objects of type T
     */
    public static <T extends Enum<T>> List<T> inspectConstants(final Class<T> clazz, boolean containsNull) {
        List<T> list = new ArrayList<T>(Arrays.asList(clazz.getEnumConstants()));
        if (!containsNull) {
            list.remove(0);
        }
        return list;
    }

	/**
	 * Translates code into its corresponding enum instance.
	 * <p>
	 * NOTE: To make this function work, please DO implement the following method in your Enum
	 * class:
	 * 
	 * <pre>
	 * public String getCode() {
	 * 	// return the unique code
	 * 	return &quot;CODE&quot;;
	 * }
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param <T>
	 *            type of enum
	 * @param clazz
	 *            the type of enum instance expected
	 * @param code
	 *            the unique customized code for the enum instance. Usually, the code is the
	 *            used in underlying database table.
	 * @return an instance of type T, or null if the code is not defined
	 */
	public static <T extends Enum<T>> T translate(final Class<T> clazz, final String code) {
	    if(code==null){
	        return null;
	    }
		try {
			final Method m = clazz.getDeclaredMethod(GET_CODE_METHOD);
			for (T t : inspectConstants(clazz)) {
				if (code.equals(m.invoke(t))) {
					return t;
				}
			}
		} catch (Exception e) { // NOSONAR
		    LOGGER.error("failed to translate code {} into object of type {}", code, clazz);
		}
		
		return null;
	}
	
	public static <T extends Enum<T>> T dbTranslate(final Class<T> clazz, final Integer code) {
	    if(code==null){
	        return null;
	    }
		try {
			final Method m = clazz.getDeclaredMethod(GET_CODE_METHOD);
			for (T t : inspectConstants(clazz)) {
				if (code.equals(m.invoke(t))) {
					return t;
				}
			}
		} catch (Exception e) { // NOSONAR
		    LOGGER.error("failed to translate code {} into object of type {}", code, clazz);
		}
		
		return null;
	}
	
	/**
	 * Retrieve code value of certain Enum instance, this method is null-safe.
	 * i.e. returns null if input instance is null.
	 * 
	 * @param <T>
	 * @param clazz
	 * @param obj
	 * @return code
	 */
	public static <T extends Enum<T>> String getCode(final T obj) {
		if (obj == null) {
			return null;
		}
		try {
			Class<?> clazz = obj.getClass();
			final Method m = clazz.getDeclaredMethod(GET_CODE_METHOD);
			return m.invoke(obj).toString();
		} catch (Exception e) { // NOSONAR
			// ignore
		}
		return null;
	}
	
	/**
	 * Translates label into its corresponding enum instance.
	 * @param <T>
	 * @param clazz
	 * @param label
	 * @return
	 */
	public static <T extends Enum<T>> T translateByLabel(final Class<T> clazz, final String label) {
	    if(label ==null){
	        return null;
	    }
		try {
			final Method m = clazz.getDeclaredMethod(GET_LABEL_METHOD);
			for (T t : inspectConstants(clazz)) {
				if (label.equals(m.invoke(t))) {
					return t;
				}
			}
		} catch (Exception e) { // NOSONAR
			// ignore
			LOGGER.error("failed to translate label {} into object of type {}", label, clazz);
		}
		return null;
	} 

    /**
     * Get a List of option by Enum class.
     * 
     * @param clazz
     * @param containsNull
     * @return
     */
    public static <T extends Enum<T> & PageEnum> List<EnumOption> getOptionsByEnum(
            final Class<T> clazz) {
        List<T> enums = inspectConstants(clazz);
        List<EnumOption> options = Lists.transform(enums, new Function<T, EnumOption>() {

            @Override
            public EnumOption apply(T t) {
                return new EnumOption(t);
            }

        });
        return options;
    }
    
    public static List<EnumOption> getOptions(PageEnum... pageEnums) {
        List<EnumOption> result = Lists.newArrayList();
        for (PageEnum pageEnum : pageEnums) {
            result.add(new EnumOption(pageEnum));
        }
        return result;
    }

    /**
     * Get a List of Option without those in pageEnums
     * 
     * @param clazz
     * @param pageEnums
     * @return
     */
    public static <T extends Enum<T> & PageEnum> List<EnumOption> getOptionsWithout(final Class<T> clazz,
            PageEnum... pageEnums) {
        Set<T> temp = Sets.newHashSet(inspectConstants(clazz));
        for (PageEnum pageEnum : pageEnums) {
            temp.remove(pageEnum);
        }
        List<T> enums = Lists.newArrayList(temp);
        Collections.sort(enums);
        List<EnumOption> result = Lists.newArrayList();
        for (PageEnum pageEnum : enums) {
            result.add(new EnumOption(pageEnum));
        }
        return result;
    }

}
