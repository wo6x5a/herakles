package com.lcw.herakles.platform.common.converter;

import java.lang.reflect.ParameterizedType;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.enumeration.PageEnum;
import com.lcw.herakles.platform.common.util.EnumHelper;

/**
 * @author chenwulou
 *
 * @param <T>
 */
public abstract class DefaultAttributeConverter<T extends Enum<T> & PageEnum> implements
		AttributeConverter<T, String> {
	
	@Override
	public String convertToDatabaseColumn(T attribute) {
		return attribute.getCode();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T convertToEntityAttribute(String dbData) {
		Class<T> enumClazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return EnumHelper.translate(enumClazz, dbData);
	}

}
