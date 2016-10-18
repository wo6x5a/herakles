package com.lcw.herakles.platform.common.entity.id;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.extern.slf4j.Slf4j;

/**
 * Injects identifiers automatically for target entities
 * 
 * @author chenwulou
 *
 */
@Slf4j
public class IdInjectionEntityListener {

	private static final Map<Class<?>, Field> ID_FIELD_MAP = new ConcurrentHashMap<Class<?>, Field>();

	/**
	 * 
	 * This method will set id and may set createTs of the entity. And the id it
	 * sets depends on the implementation of<tt>produceId</tt> method.
	 * 
	 * @param entity
	 */
	@PrePersist
	public void prePersist(Object entity) {
		injectId(entity);
//		injectCreateTs(entity);
//		injectCreateOpId(entity);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This method is made protected for overriding.
	 * 
	 * @param entity
	 * @return
	 */
	protected Object produceId(Object entity) {
		return IdUtil.produce();
	}

//	private void injectCreateTs(Object entity) {
//		if (entity instanceof BasePo) {
//			BasePo e = (BasePo) entity;
//			if (e.getCreateTs() == null) {
//				e.setCreateTs(new Date());
//			}
//		}
//	}

//	private void injectCreateOpId(Object entity) {
//		if (entity instanceof BasePo) {
//			BasePo e = (BasePo) entity;
//			if (e.getCreateOpId() == null) {
//				e.setCreateOpId(SecurityContext.getInstance().getCurrentOperatorId());
//			}
//		}
//
//	}

	private void injectId(Object entity) {
		try {
			Field idField = findId(entity);
			if (idField.get(entity) == null) {
				Object id = produceId(entity);
				idField.set(entity, id);
			}
		} catch (Exception e) {
			log.warn("error while injecting id", e);
			throw new RuntimeException("unable to inject id", e);
		}
	}

	private static Field findId(Object entity) {
		Class<?> clz = entity.getClass();
		if (ID_FIELD_MAP.containsKey(clz)) {
			return ID_FIELD_MAP.get(clz);
		}
		while (clz != Object.class) {
			for (Field f : clz.getDeclaredFields()) {
				if (f.isAnnotationPresent(Id.class)) {
					if (f.getType() != String.class) {
						throw new IllegalArgumentException("invalid id type, unable to inject: " + f.getType());
					}
					f.setAccessible(true);
					ID_FIELD_MAP.put(entity.getClass(), f);
					return f;
				}
			}

			clz = clz.getSuperclass();
		}
		throw new IllegalArgumentException("unable to find id field for class: " + entity.getClass());
	}
}
