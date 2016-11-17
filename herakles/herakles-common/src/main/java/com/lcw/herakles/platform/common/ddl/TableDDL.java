package com.lcw.herakles.platform.common.ddl;

/**
 * @author chenwulou
 *
 */
public abstract class TableDDL implements DDL {

	protected Class<?> entityClass;

	protected final boolean rebuild;

	protected TableHeadDDL tableHeadDDL;

	protected FieldDDL fieldDDL;

	public TableDDL(boolean rebuild) {
		this.rebuild = rebuild;
	}

	public TableDDL(Class<?> entityClass, boolean rebuild) {
		this.entityClass = entityClass;
		this.rebuild = rebuild;
		initEntityInfo();
	}

	public abstract String createDatabase(String dbName, String username, String password);
	
	public abstract String createTableScript();
	
	public abstract String dropTableScript();

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
		initEntityInfo();
	}

	protected void initEntityInfo() {
	}
}
