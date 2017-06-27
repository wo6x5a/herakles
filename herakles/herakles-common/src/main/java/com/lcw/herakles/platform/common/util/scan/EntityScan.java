package com.lcw.herakles.platform.common.util.scan;

import java.lang.annotation.Annotation;

import javax.persistence.Entity;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class EntityScan extends ClassScan {

	@Override
	protected Iterable<Class<?>> filter(Iterable<Class<?>> classInfos) {
		return Iterables.filter(classInfos, new Predicate<Class<?>>() {

			@Override
			public boolean apply(Class<?> myClass) {
				Preconditions.checkNotNull(myClass);
				Annotation entity = myClass.getAnnotation(Entity.class);
				return null != entity;
			}

		});
	}
	
}
