package com.lcw.herakles.platform.common.ddl;

/**
 * @author chenwulou
 *
 */
public abstract class TableHeadDDL implements DDL {

	protected String tableName;

	protected final boolean rebuild;

	public TableHeadDDL(Class<?> entityClass, boolean rebuild) {
		this.tableName = DDLUtil.getTableName(entityClass);
		this.rebuild = rebuild;
	}

	public abstract void appendTableHead(StringBuilder sb);
	
	public abstract String dropTableDDL();

	public String getTableName() {
		return this.tableName;
	}
}
