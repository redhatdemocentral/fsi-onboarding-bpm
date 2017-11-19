package com.redhat.bpms.demo.fsi.onboarding.config;

public class SystemPropertiesEnvironment implements Environment {

	private static final String ENTANDO_BASE_URL = System.getProperty("fsi.demo.entando.base.url", DEFAULT_ENTANDO_BASE_URL);
		
	@Override
	public String getEntandoBaseUrl() {
		return ENTANDO_BASE_URL;
	}
	
}
