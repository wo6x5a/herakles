package com.lcw.herakles.platform.common.ddl.dialect.oracle;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;

import com.lcw.herakles.platform.common.ddl.FieldDDL;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;

public class OracleFieldDDL extends FieldDDL {

	@Override
	public void appendColumnMetaData(StringBuilder sb) {
		ColumnMeta columnMeta = field.getAnnotation(ColumnMeta.class);
		if (null != columnMeta) {
			String defaultValue = columnMeta.defaultValue();
			if (!StringUtils.equalsIgnoreCase("NULL", defaultValue)) {
				sb.append(SPACE).append("DEFAULT").append(SPACE).append("'").append(defaultValue).append("'");
			} else {
				boolean nullable = columnMeta.nullable();
				if (!nullable)
					sb.append(SPACE).append("NOT NULL");
			}
		}
	}

	@Override
	protected void appendStringType(StringBuilder sb) {
		if (StringUtils.isBlank(fieldLength)) {
			sb.append("VARCHAR2(255 CHAR)");
		} else if (Integer.valueOf(fieldLength) > 1000) {
			sb.append("CLOB");
		} else {
			sb.append("VARCHAR2(" + fieldLength + SPACE + "CHAR)");
		}
	}

	@Override
	protected void appendNumberType(StringBuilder sb) {
		if (StringUtils.isBlank(fieldLength)) {
			sb.append("NUMBER(17,2)");
		} else {
			sb.append("NUMBER(").append(fieldLength).append(")");
		}
	}

	@Override
	protected void appendVersion(StringBuilder sb) {
		sb.append("NUMBER(17)");
	}

	@Override
	protected void appendDateType(StringBuilder sb) {
		Temporal isTemporal = field.getAnnotation(Temporal.class);
		if (null != isTemporal && isTemporal.value() == TemporalType.TIMESTAMP)
			sb.append("TIMESTAMP");
		else
			sb.append("DATE");
	}

	@Override
	protected void appendIntegerType(StringBuilder sb) {
		if (StringUtils.isBlank(fieldLength)) {
			sb.append("NUMBER(10, 0)");
		} else {
			sb.append("NUMBER(").append(fieldLength).append(")");
		}
	}

}
