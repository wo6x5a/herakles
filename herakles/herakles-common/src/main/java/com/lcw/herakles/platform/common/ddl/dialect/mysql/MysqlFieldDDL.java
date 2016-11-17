package com.lcw.herakles.platform.common.ddl.dialect.mysql;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;

import com.lcw.herakles.platform.common.ddl.FieldDDL;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;

public class MysqlFieldDDL extends FieldDDL {

	@Override
	public void appendColumnMetaData(StringBuilder sb) {
		ColumnMeta columnMeta = field.getAnnotation(ColumnMeta.class);
		if (null != columnMeta) {
			boolean nullable = columnMeta.nullable();
			if (!nullable)
				sb.append(SPACE).append("NOT NULL");
			String defaultValue = columnMeta.defaultValue();
			if (!StringUtils.equalsIgnoreCase("NULL", defaultValue))
				sb.append(SPACE).append("DEFAULT").append(SPACE).append("'").append(defaultValue)
						.append("'");
			String comment = columnMeta.comment();
			if (StringUtils.isNotEmpty(comment)) {
				sb.append(SPACE).append("COMMENT").append(SPACE);
				sb.append("'").append(comment).append("'");
			}
		}
	}

	@Override
	protected void appendStringType(StringBuilder sb) {
		if (StringUtils.isBlank(fieldLength)) {
			sb.append("varchar(255)");
		} else if (Integer.valueOf(fieldLength) > 255) {
			sb.append("text");
		} else {
			sb.append("varchar(").append(fieldLength).append(")");
		}
	}

	@Override
	protected void appendNumberType(StringBuilder sb) {
		if (StringUtils.isBlank(fieldLength)) {
			sb.append("decimal(17,2)");
		} else {
			sb.append("decimal(").append(fieldLength).append(")");
		}
	}

	@Override
	protected void appendVersion(StringBuilder sb) {
		sb.append("decimal(17,0)");
	}

	@Override
	protected void appendDateType(StringBuilder sb) {
		Temporal isTemporal = field.getAnnotation(Temporal.class);
		if (null != isTemporal && isTemporal.value() == TemporalType.TIMESTAMP)
			sb.append("datetime(6)");
		else
			sb.append("date");
	}

	@Override
	protected void appendIntegerType(StringBuilder sb) {
		sb.append("INTEGER");
	}

}
