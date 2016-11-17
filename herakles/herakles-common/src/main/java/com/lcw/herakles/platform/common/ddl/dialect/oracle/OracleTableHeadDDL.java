package com.lcw.herakles.platform.common.ddl.dialect.oracle;

import java.text.MessageFormat;

import com.lcw.herakles.platform.common.ddl.TableHeadDDL;

/**
 * @author chenwulou
 *
 */
public class OracleTableHeadDDL extends TableHeadDDL {

	public OracleTableHeadDDL(Class<?> entityClass, boolean rebuild) {
		super(entityClass, rebuild);
	}

	@Override
	public void appendTableHead(StringBuilder sb) {
		if (rebuild) {
			sb.append(dropTableDDL());
		}
		sb.append(MessageFormat.format("CREATE TABLE {0} (\n", tableName));
	}
	
	@Override
	public String dropTableDDL(){
		return MessageFormat.format("DROP TABLE {0} CASCADE CONSTRAINTS;\n", tableName);
	}
}
