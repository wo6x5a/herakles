package com.lcw.herakles.platform.common.ddl.dialect;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.lcw.herakles.platform.common.ddl.DDLUtil;
import com.lcw.herakles.platform.common.ddl.TableDDL;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.ddl.dialect.mysql.MysqTableHeadDDL;
import com.lcw.herakles.platform.common.ddl.dialect.mysql.MysqlFieldDDL;
import com.lcw.herakles.platform.common.util.ReflectionUtil;

/**
 * @author chenwulou
 *
 */
public class MysqlTableDDL extends TableDDL {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MysqlTableDDL.class);

	public MysqlTableDDL(Class<?> entityClass, boolean rebuild) {
		super(entityClass, rebuild);
	}

	public MysqlTableDDL(boolean rebuild) {
		super(rebuild);
	}

	@Override
	public String createDatabase(String dbName, String username, String password) {
		StringBuilder sql = new StringBuilder();
		if (rebuild) {
			sql.append("DROP DATABASE").append(SPACE).append("IF EXISTS").append(SPACE)
					.append(dbName).append(SEMICOLON + ENTER);
		}
		sql.append("CREATE DATABASE").append(SPACE).append(dbName).append(SPACE);
		sql.append("DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci").append(SEMICOLON + ENTER);
		
		//sql.append("USE").append(SPACE).append(dbName).append(SEMICOLON + ENTER);
		
		sql.append("DROP USER").append(SPACE).append(QUOTE).append(username).append(QUOTE).append("@").append(QUOTE).append("localhost").append(QUOTE);
		sql.append(SEMICOLON + ENTER);
		
		sql.append("CREATE USER").append(SPACE);
		sql.append(QUOTE).append(username).append(QUOTE).append("@").append(QUOTE).append("localhost").append(QUOTE);
		sql.append(SPACE).append("IDENTIFIED BY").append(SPACE).append(QUOTE).append(password).append(QUOTE)
				.append(SEMICOLON + ENTER);

		sql.append("GRANT ALL PRIVILEGES ON").append(SPACE).append(dbName + ".*").append(SPACE);
		sql.append("TO").append(SPACE).append(QUOTE).append(username).append(QUOTE).append("@").append(QUOTE).append("localhost").append(QUOTE);
		sql.append(SPACE).append("IDENTIFIED BY").append(SPACE).append(QUOTE).append(password).append(QUOTE)
				.append(SEMICOLON + ENTER);

		sql.append("DROP USER").append(SPACE).append(QUOTE).append(username).append(QUOTE).append("@").append(QUOTE).append("%").append(QUOTE);
		sql.append(SEMICOLON + ENTER);
		
		sql.append("CREATE USER").append(SPACE);
		sql.append(QUOTE).append(username).append(QUOTE).append("@").append(QUOTE).append("%").append(QUOTE);
		sql.append(SPACE).append("IDENTIFIED BY").append(SPACE).append(QUOTE).append(password).append(QUOTE)
				.append(SEMICOLON + ENTER);

		sql.append("GRANT ALL PRIVILEGES ON").append(SPACE).append(dbName + ".*").append(SPACE);
		sql.append("TO").append(SPACE).append(QUOTE).append(username).append(QUOTE).append("@").append(QUOTE).append("%").append(QUOTE);
		sql.append(SPACE).append("IDENTIFIED BY").append(SPACE).append(QUOTE).append(password).append(QUOTE)
				.append(SEMICOLON + ENTER);

		sql.append("FLUSH PRIVILEGES").append(SEMICOLON + ENTER);

		return sql.toString();
	}

	@Override
	public String createTableScript() {
		StringBuilder sb = new StringBuilder();
		if (DDLUtil.hasColumnMeta(entityClass)) {
			List<Field> fields = ReflectionUtil.getFieldsIncludingSuperClasses(entityClass);
			tableHeadDDL.appendTableHead(sb);
			fieldDDL.appendFields(sb, fields);
			appendKey(sb, fields);
			appanedTableEnd(sb);
		}
		return sb.toString();
	}


	@Override
	protected void initEntityInfo() {
		super.initEntityInfo();
		tableHeadDDL = new MysqTableHeadDDL(entityClass, rebuild);
		fieldDDL = new MysqlFieldDDL();
	}

	/**
	 * @param sb
	 * @param fields
	 */
	private void appendKey(StringBuilder sb, List<Field> fields) {
		String primaryKey = null;
		Map<String, String> keys = Maps.newHashMap();
		Map<String, String> uniqueKeys = Maps.newHashMap();
		for (Field field : fields) {
			primaryKey = initPrimaryKey(primaryKey, field);
			initKey(keys, field);
			initUniqueKey(uniqueKeys, field);
		}
		appendPrimaryKey(sb, primaryKey);
		appendKey(sb, keys);
		appendUniqueKey(sb, uniqueKeys);
	}

	/**
	 * @param sb
	 * @param uniqueKeys
	 */
	private void appendUniqueKey(StringBuilder sb, Map<String, String> uniqueKeys) {
		if (!CollectionUtils.isEmpty(uniqueKeys)) {
			for (Entry<String, String> uniqueKey : uniqueKeys.entrySet()) {
				sb.append("UNIQUE KEY " + uniqueKey.getKey() + " (" + uniqueKey.getValue() + "),"
						+ ENTER);
			}
		}
	}

	/**
	 * @param uniqueKeys
	 * @param field
	 */
	private void initUniqueKey(Map<String, String> uniqueKeys, Field field) {
		ColumnMeta columnMeta = field.getAnnotation(ColumnMeta.class);
		Column column = field.getAnnotation(Column.class);
		if (null != columnMeta && StringUtils.isNotEmpty(columnMeta.uniqueKey())) {
			uniqueKeys.put(columnMeta.uniqueKey(), column.name());
		}
	}

	/**
	 * @param sb
	 * @param keys
	 */
	private void appendKey(StringBuilder sb, Map<String, String> keys) {
		appendUniqueKey(sb, keys);
	}

	/**
	 * @param keys
	 * @param field
	 */
	private void initKey(Map<String, String> keys, Field field) {
		ColumnMeta columnMeta = field.getAnnotation(ColumnMeta.class);
		Column column = field.getAnnotation(Column.class);
		if (null != columnMeta && StringUtils.isNotEmpty(columnMeta.key())) {
			keys.put(columnMeta.key(), column.name());
		}
	}

	/**
	 * @param sb
	 * @param primaryKey
	 */
	private void appendPrimaryKey(StringBuilder sb, String primaryKey) {
		if (StringUtils.isNotBlank(primaryKey))
			sb.append("PRIMARY KEY (").append(primaryKey).append("),").append(ENTER);
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

	/**
	 * @param sb
	 * @return
	 */
	private void appanedTableEnd(StringBuilder sb) {
		DDLUtil.removeComma(sb);
		sb.append(")");
		sb.append(SPACE);
		sb.append("ENGINE=InnoDB DEFAULT CHARSET=utf8;");
	}

	@Override
	public String dropTableScript() {
		return tableHeadDDL.dropTableDDL();
	}

}

