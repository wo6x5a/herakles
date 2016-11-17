package com.lcw.herakles.platform.common.ddl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

/**
 * @author chenwulou
 *
 */
public abstract class FieldDDL implements DDL {

	protected Field field;

	protected Class<?> fieldType;

	protected String fieldLength;
	
	public void appendFields(StringBuilder sb, List<Field> fields) {
		DDLUtil.sortFields(fields);
		for (Field field : fields) {
			Column column = field.getAnnotation(Column.class);
			if (null != column) {
				String columnName = column.name();
				sb.append(TAB + columnName + SPACE);
				appendType(sb, field);
				appendColumnMetaData(sb);
				sb.append(COMMA + ENTER);
			}
		}
	}

	protected abstract void appendColumnMetaData(StringBuilder sb);

	protected abstract void appendStringType(StringBuilder sb);

	protected abstract void appendNumberType(StringBuilder sb);

	protected abstract void appendVersion(StringBuilder sb);

	protected abstract void appendDateType(StringBuilder sb);

	protected abstract void appendIntegerType(StringBuilder sb);

	private void appendType(StringBuilder sb, Field field) {
		initFieldInfo(field);
		if (fieldType.isAssignableFrom(String.class) || fieldType.isEnum()) {
			appendStringType(sb);
		} else if (fieldType.isAssignableFrom(BigDecimal.class)) {
			appendNumberType(sb);
		} else if (fieldType.isAssignableFrom(Long.class)) {
			appendVersion(sb);
		} else if (fieldType.isAssignableFrom(Date.class)) {
			appendDateType(sb);
		} else if (fieldType.isAssignableFrom(Integer.class)) {
			appendIntegerType(sb);
		}
	}
	
	private void initFieldInfo(Field field) {
		this.field = field;
		this.fieldType = field.getType();
		this.fieldLength = DDLUtil.getTypeLength(field);
	}
}
