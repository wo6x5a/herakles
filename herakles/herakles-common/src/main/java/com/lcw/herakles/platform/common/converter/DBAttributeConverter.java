package com.lcw.herakles.platform.common.converter;

import java.lang.reflect.ParameterizedType;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.enums.DBEnum;
import com.lcw.herakles.platform.common.util.EnumHelper;

/**
 * @author chenwulou
 *
 * @param <T>
 */
public abstract class DBAttributeConverter<T extends Enum<T> & DBEnum> implements AttributeConverter<T, Integer> {

	@Override
	public Integer convertToDatabaseColumn(T attribute) {
		return attribute.getCode();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T convertToEntityAttribute(Integer dbData) {
		Class<T> enumClazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		return EnumHelper.dbTranslate(enumClazz, dbData);
	}

}
