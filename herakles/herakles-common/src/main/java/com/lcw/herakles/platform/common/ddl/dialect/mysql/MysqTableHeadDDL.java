package com.lcw.herakles.platform.common.ddl.dialect.mysql;

import com.lcw.herakles.platform.common.ddl.TableHeadDDL;

public class MysqTableHeadDDL extends TableHeadDDL {

	public MysqTableHeadDDL(Class<?> entityClass, boolean rebuild) {
		super(entityClass, rebuild);
	}

	@Override
	public void appendTableHead(StringBuilder sb) {
		if (rebuild) {
			sb.append(dropTableDDL());
		}
		sb.append("CREATE TABLE " + tableName + " (" + ENTER);
	}
	
	@Override
	public String dropTableDDL(){
		return "DROP TABLE IF EXISTS " + tableName + SEMICOLON + ENTER;
	}
}
