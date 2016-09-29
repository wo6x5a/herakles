/**
 * 
 */
package com.lcw.herakles.platform.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * @author chenwulou
 *
 */
public final class ReflectionUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    private ReflectionUtil() {

    }

	public static Method getGetter(Class<?> clazz, Field field) {
		String filedName = field.getName();
		String firstLetter = filedName.substring(0, 1).toUpperCase();
		String getMethodName = "get" + firstLetter + filedName.substring(1);
		Method getMethod = null;
		try {
			getMethod = clazz.getDeclaredMethod(getMethodName);
		} catch (Exception e) {
			LOGGER.error("error while get getter:", e);
		}
		return getMethod;
	}

	public static Method getSetter(Class<?> clazz, Field field) {
		Class<?> fieldType = field.getType();
		String filedName = field.getName();
		String firstLetter = filedName.substring(0, 1).toUpperCase();
		String setMethodName = "set" + firstLetter + filedName.substring(1);
		Method setMethod = null;
		try {
			setMethod = clazz.getDeclaredMethod(setMethodName, fieldType);
		} catch (Exception e) {
			LOGGER.error("error while get setter:", e);
		}
		return setMethod;
	}

	public static Map<String, String> getFieldMap(Object o) {
		Class<?> clazz = o.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Map<String, String> fieldMap = Maps.newHashMap();
		for (Field field : fields) {
			String fieldName = field.getName().toUpperCase();
			Method getMethod = ReflectionUtil.getGetter(clazz, field);
			String fieldValue = null;
			try {
				fieldValue = getMethod.invoke(o).toString();
			} catch (Exception e) {
				LOGGER.error("error while get: {}", e, fieldName);
			}
			if (null != fieldValue)
				fieldMap.put(fieldName, fieldValue);
		}
		return fieldMap;
	}
}
