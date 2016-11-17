package com.lcw.herakles.platform.common.ddl.dialect;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lcw.herakles.platform.common.ddl.DDLUtil;
import com.lcw.herakles.platform.common.ddl.TableDDL;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.ddl.dialect.oracle.OracleFieldDDL;
import com.lcw.herakles.platform.common.ddl.dialect.oracle.OracleTableHeadDDL;
import com.lcw.herakles.platform.common.util.ReflectionUtil;

/**
 * @author chenwulou
 *
 */
public class OracleTableDDL extends TableDDL {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(OracleTableDDL.class);

	private final String tableSpace;
	private final String indexTableSpace;
	private final String histTableSpace;
	private final String dataFilePath;
	private final int datafileSize = 512;
	private final int nextDatafileSize = 1024;
	private final int maxDatafileSize = 5120;

	public OracleTableDDL(Class<?> entityClass, boolean rebuild, String tableSpace,
			String indexTableSpace, String histTableSpace, String dataFilePath) {
		super(entityClass, rebuild);
		this.tableSpace = tableSpace;
		this.indexTableSpace = indexTableSpace;
		if(StringUtils.isBlank(histTableSpace)){
			histTableSpace = tableSpace;
		}
		this.histTableSpace = histTableSpace;
		this.dataFilePath = dataFilePath;
	}

	public OracleTableDDL(boolean rebuild, String tableSpace, 
			String indexTableSpace, String histTableSpace, String dataFilePath) {
		super(rebuild);
		this.tableSpace = tableSpace;
		this.indexTableSpace = indexTableSpace;
		if(StringUtils.isBlank(histTableSpace)){
			histTableSpace = tableSpace;
		}
		this.histTableSpace = histTableSpace;
		this.dataFilePath = dataFilePath;
	}

	@Override
	public String createDatabase(String dbName, String username, String password) {
		StringBuilder scripts = new StringBuilder();
		if(rebuild){
			dropTableSpace(scripts);
		}
		createTableSpaces(scripts);
		if(rebuild){
			dropUser(scripts, username);
		}
		createUser(scripts, username, password);
		grantUser(scripts, username);
		return scripts.toString();
	}
	
	private void dropTableSpace(StringBuilder scripts){
		scripts.append(ENTER);
		scripts.append("-- drop tablespaces.").append(ENTER);
		scripts.append("drop tablespace ").append(tableSpace).append(" including contents and datafiles").append(SEMICOLON).append(ENTER);
		scripts.append("drop tablespace ").append(indexTableSpace).append(" including contents and datafiles").append(SEMICOLON).append(ENTER);
		if(!StringUtils.equalsIgnoreCase(tableSpace, histTableSpace)){
			scripts.append("drop tablespace ").append(histTableSpace).append(" including contents and datafiles").append(SEMICOLON).append(ENTER);
		}
	}
	private void createTableSpaces(StringBuilder scripts){
		createTableSpace(scripts, tableSpace);
		createTableSpace(scripts, indexTableSpace);
		if(!StringUtils.equalsIgnoreCase(tableSpace, histTableSpace)){
			createTableSpace(scripts, histTableSpace);
		}
	}
	private void createTableSpace(StringBuilder scripts, String tablespace){
		scripts.append(ENTER);
		scripts.append("-- create tablespace ").append(tablespace).append(ENTER);
		scripts.append("create tablespace ").append(tablespace).append(ENTER);
		scripts.append(" logging").append(ENTER);
		scripts.append(TAB).append("datafile ").append(QUOTE).append(dataFilePath).append(tablespace.toUpperCase()+".dbf").append(QUOTE).append(ENTER);
		scripts.append(" size ").append(datafileSize).append("m").append(ENTER);
		scripts.append(" autoextend on").append(ENTER);
		scripts.append(" next ").append(nextDatafileSize).append("m").append(" maxsize ").append(maxDatafileSize).append("m").append(ENTER);
		scripts.append(" extent management local;").append(ENTER);
	}
	
	private void dropUser(StringBuilder scripts, String username){
		scripts.append(ENTER);
		scripts.append("-- drop user ").append(username).append(ENTER);
		scripts.append("drop user ").append(username).append(" cascade").append(SEMICOLON).append(ENTER);
	}
	private void createUser(StringBuilder scripts, String username, String password){
		scripts.append(ENTER);
		scripts.append("-- create user ").append(username).append(ENTER);
		scripts.append("create user ").append(username).append(" identified by ").append(password).append(ENTER);
		scripts.append(" default tablespace ").append(tableSpace).append(ENTER);
		scripts.append(" temporary tablespace temp").append(SEMICOLON).append(ENTER);
	}
	private void grantUser(StringBuilder scripts, String username){
		scripts.append(ENTER);
		scripts.append("-- grant privileges to user ").append(username).append(ENTER);
		scripts.append("GRANT CREATE ANY DIRECTORY TO ").append(username).append(SEMICOLON).append(ENTER);
		scripts.append("GRANT UNLIMITED TABLESPACE TO ").append(username).append(SEMICOLON).append(ENTER);
		scripts.append("GRANT CONNECT TO ").append(username).append(SEMICOLON).append(ENTER);
		scripts.append("GRANT RESOURCE TO ").append(username).append(SEMICOLON).append(ENTER);
		scripts.append("GRANT CREATE ANY VIEW TO ").append(username).append(SEMICOLON).append(ENTER);
		scripts.append("GRANT DROP ANY VIEW TO ").append(username).append(SEMICOLON).append(ENTER);
	}

	@Override
	public String createTableScript() {
		StringBuilder sb = new StringBuilder();
		if (DDLUtil.hasColumnMeta(entityClass)) {
			List<Field> fields = ReflectionUtil.getFieldsIncludingSuperClasses(entityClass);
			tableHeadDDL.appendTableHead(sb);
			fieldDDL.appendFields(sb, fields);
			appanedTableEnd(sb);
			appendComment(sb, fields);
			appendConstraint(sb, fields);
		}
		sb.append(ENTER);
		return sb.toString();
	}

	@Override
	protected void initEntityInfo() {
		super.initEntityInfo();
		this.tableHeadDDL = new OracleTableHeadDDL(entityClass, rebuild);
		this.fieldDDL = new OracleFieldDDL();
	}

	/**
	 * @param sb
	 * @return
	 */
	private void appanedTableEnd(StringBuilder sb) {
		DDLUtil.removeComma(sb);
		sb.append(MessageFormat.format(" ) tablespace {0};\n", appendTableSpace()));
	}
	
	private String appendTableSpace(){
		String tableName = tableHeadDDL.getTableName();
		boolean hist = StringUtils.endsWithIgnoreCase(tableName, "_HIST");
		return hist?histTableSpace:tableSpace;
	}

	private void appendComment(StringBuilder sb, List<Field> fields) {
		String tableName = tableHeadDDL.getTableName();
		for (Field field : fields) {
			ColumnMeta columnMeta = field.getAnnotation(ColumnMeta.class);
			if (null != columnMeta && !StringUtils.equals(StringUtils.EMPTY, columnMeta.comment())) {
				Column column = field.getAnnotation(Column.class);
				String columnName = column.name();
				String comment = columnMeta.comment();
				/*
				sb.append(MessageFormat.format("COMMENT ON COLUMN {0}.{1}\nIS\n''{2}'' ;\n",
						tableName, columnName, comment));
				*/
				sb.append(MessageFormat.format("COMMENT ON COLUMN {0}.{1} IS ''{2}'' ;\n",
						tableName, columnName, comment));
			}
		}
	}

	private void appendConstraint(StringBuilder sb, List<Field> fields) {
		appendPrimaryKey(sb, fields);
		appendUniqueKey(sb, fields);
	}

	private void appendPrimaryKey(StringBuilder sb, List<Field> fields) {
		String tableName = tableHeadDDL.getTableName();
		String constraint = tableName + "_PK";
		//String primaryKey = PersistenceUtil.getIdColumnName(entityClass);
		String primaryKey = null;
		for(Field field : fields){
			primaryKey = initPrimaryKey(primaryKey, field);
		}
		sb.append(MessageFormat
				.format("ALTER TABLE {0} ADD CONSTRAINT {1} PRIMARY KEY ( {2} ) USING INDEX TABLESPACE {3} ;\n",
						tableName, constraint, primaryKey, indexTableSpace));
	}

	/**
	 * @param primaryKey
	 * @param field
	 * @return
	 */
	private String initPrimaryKey(String primaryKey, Field field) {
		Id isId = field.getAnnotation(Id.class);
		if (null != isId) {
			Column column = field.getAnnotation(Column.class);
			if (StringUtils.isNotBlank(primaryKey)) {
				primaryKey = primaryKey + COMMA + column.name();
			} else {
				primaryKey = column.name();
			}
		}
		return primaryKey;
	}

	private void appendUniqueKey(StringBuilder sb, List<Field> fields) {
		String tableName = tableHeadDDL.getTableName();
		int i = 1;
		for (Field field : fields) {
			ColumnMeta columnMeta = field.getAnnotation(ColumnMeta.class);
			if (null != columnMeta
					&& !StringUtils.equals(StringUtils.EMPTY, columnMeta.uniqueKey())) {
				String uniqueKey = columnMeta.uniqueKey();
				String constraint = tableName + "_UK_" + i;
				i++;
				sb.append(MessageFormat
						.format("ALTER TABLE {0} ADD CONSTRAINT {1} UNIQUE ( {2} ) USING INDEX TABLESPACE {3};\n",
								tableName, constraint, uniqueKey, indexTableSpace));
			}
		}
	}

	@Override
	public String dropTableScript() {
		return tableHeadDDL.dropTableDDL();
	}

}
