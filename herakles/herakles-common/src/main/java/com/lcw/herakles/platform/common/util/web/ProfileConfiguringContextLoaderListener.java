package com.lcw.herakles.platform.common.util.web;

import javax.servlet.ServletContextEvent;

import org.springframework.core.env.AbstractEnvironment;
import org.springframework.web.context.ContextLoaderListener;

/**
 * Configure profile from web init param before initializing Spring.
 * 
 * @author chenwulou
 *
 */
public class ProfileConfiguringContextLoaderListener extends
		ContextLoaderListener {

	private static final String PROFILE_PROPERTY = "spring.profile";
	private static final String DEFAULT_PROFILE_PROPERTY = "spring.profile.default";

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String profile = event.getServletContext().getInitParameter(PROFILE_PROPERTY);
		if (profile.startsWith("$")) {
			profile = event.getServletContext().getInitParameter(DEFAULT_PROFILE_PROPERTY);
		}
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, profile);
		super.contextInitialized(event);
	}

}
