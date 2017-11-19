package com.redhat.bpms.demo.fsi.onboarding.config;

public class EnvironmentVariablesEnvironment implements Environment {
	 
	private static final String ENTANDO_BASE_URL = System.getenv("ENTANDO_BASE_URL");
	
	@Override
	public String getEntandoBaseUrl() {
		if (ENTANDO_BASE_URL != null) {
			return ENTANDO_BASE_URL;
		} else {
			return DEFAULT_ENTANDO_BASE_URL;
		}
	}
	
}
