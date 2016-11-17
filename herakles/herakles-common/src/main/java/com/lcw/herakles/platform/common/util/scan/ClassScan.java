package com.lcw.herakles.platform.common.util.scan;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.lcw.herakles.platform.common.constant.ApplicationConsts;

/**
 * @author chenwulou
 *
 */
public abstract class ClassScan {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassScan.class);

	public ImmutableSet<Class<?>> getEntities() throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ resolveBasePackage(ApplicationConsts.PACKAGE_TO_SCAN) + "/" + "**/*.class";
		Resource[] resources = {};
		try {
			resources = resourcePatternResolver.getResources(packageSearchPath);
		} catch (IOException e) {
			LOGGER.error("cannot enumerate resources", e);
		}
		Iterable<Class<?>> rawClasses = Iterables
				.transform(Arrays.asList(resources), new ToClass());
		Iterable<Class<?>> classes = filter(rawClasses);
		return ImmutableSet.copyOf(classes);
	}
	
	protected abstract Iterable<Class<?>> filter(Iterable<Class<?>> rawClasses);

	class ToClass implements Function<Resource, Class<?>> {

		@Override
		public Class<?> apply(Resource resource) {
			if (!resource.isReadable())
				return null;
			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
			MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(
					resourcePatternResolver);
			try {
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				return Class.forName(metadataReader.getClassMetadata().getClassName());
			} catch (Exception e) {
				LOGGER.warn("cannot resolve type {}", e);
				return null;
			}
		}

	}

	private static String resolveBasePackage(String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils
				.resolvePlaceholders(basePackage));
	}

}
