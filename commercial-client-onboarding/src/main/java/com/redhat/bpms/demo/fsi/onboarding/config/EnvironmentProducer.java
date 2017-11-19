package com.redhat.bpms.demo.fsi.onboarding.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentProducer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentProducer.class);

	private static final String ENVIRONMENT_PROPERTY_NAME = "fsi.demo.environment";
	
	private static final String ENVIRONMENT = System.getProperty(ENVIRONMENT_PROPERTY_NAME, "EnvironmentVariables");
	
	private static final Environment environment;
	
	static {
		switch (ENVIRONMENT) {
		case "EnvironmentVariables": 
			LOGGER.info("Setting EnvironmentVariables environment.");
			environment = new EnvironmentVariablesEnvironment();
			break;
		case "SystemProperties": 
			LOGGER.info("Setting SystemProperties environment.");
			environment = new SystemPropertiesEnvironment();
			break;
		default:
			LOGGER.warn("No enviroment set. Returning null.");
			environment = null;
		}
	}
	
	public static Environment getEnvironment() {
		return environment;
	}
}
