package com.redhat.bpms.demo.fsi.onboarding.model;

import org.kie.api.runtime.process.ProcessContext;

public class EmailBodyContext {
	
	private final ProcessContext kcontext;
	
	private final Client client;

	public EmailBodyContext(final ProcessContext kcontext, final Client client) { 
		this.kcontext = kcontext;
		this.client = client;
	}

	public ProcessContext getKcontext() {
		return kcontext;
	}
	
	public Client getClient() {
		return client;
	}

}
