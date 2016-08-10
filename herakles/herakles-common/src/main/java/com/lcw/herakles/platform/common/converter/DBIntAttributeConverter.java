package com.lcw.herakles.platform.common.converter;

import java.lang.reflect.ParameterizedType;

import javax.persistence.AttributeConverter;

import com.lcw.herakles.platform.common.enums.DBIntEnum;
import com.lcw.herakles.platform.common.util.EnumHelper;

/**
 * @author chenwulou
 *
 * @param <T>
 */
public abstract class DBIntAttributeConverter<T extends Enum<T> & DBIntEnum> implements AttributeConverter<T, Integer> {

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
