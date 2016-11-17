package com.lcw.herakles.platform.common.ddl;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Table;

import com.google.common.base.Preconditions;
import com.lcw.herakles.platform.common.ddl.annotation.ColumnMeta;
import com.lcw.herakles.platform.common.util.ReflectionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenwulou
 *
 */
@Slf4j
public final class DDLUtil {

//	private static final Logger LOGGER = LoggerFactory.getLogger(DDLUtil.class);

	public static String getTableName(Class<?> entityClass) {
		Table entity = entityClass.getAnnotation(Table.class);
		Preconditions.checkArgument(null != entity);
		return entity.name();
	}

	public static boolean hasColumnMeta(Class<?> entityClass) {
		List<Field> fields = ReflectionUtil.getFieldsIncludingSuperClasses(entityClass);
		for (Field field : fields) {
			ColumnMeta columnMeta = field.getAnnotation(ColumnMeta.class);
			if (columnMeta != null) {
				return true;
			}
		}
		log.warn("entityClass :{} has no field with ColumnMeta annotation",
				entityClass.getSimpleName());
		return false;
	}

	public static void sortFields(List<Field> fields) {
		Collections.sort(fields, new Comparator<Field>() {
			final Integer DEFAULT_ORDER = Integer.valueOf(0);

			@Override
			public int compare(Field f1, Field f2) {
				ColumnMeta meta1 = f1.getAnnotation(ColumnMeta.class);
				ColumnMeta meta2 = f2.getAnnotation(ColumnMeta.class);
				Integer order1 = getOrder(meta1);
				Integer order2 = getOrder(meta2);
				return order1.compareTo(order2);
			}

			private Integer getOrder(ColumnMeta meta) {
				Integer order = DEFAULT_ORDER;
				if (meta != null) {
					order = Integer.valueOf(meta.order());
				}
				return order;
			}
		});
	}

	public static String getTypeLength(Field field) {
		String length = null;
		ColumnMeta columnMeta = field.getAnnotation(ColumnMeta.class);
		if (null != columnMeta) {
			length = columnMeta.length();
		}
		return length;
	}

	public static void removeComma(StringBuilder sb) {
		sb.deleteCharAt(sb.length() - 2);
	}

}
