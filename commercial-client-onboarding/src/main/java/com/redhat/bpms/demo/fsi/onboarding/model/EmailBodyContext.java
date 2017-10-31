package com.redhat.bpms.demo.fsi.onboarding.model;

import org.kie.api.runtime.process.ProcessContext;

public class EmailBodyContext {
	
	private final ProcessContext kcontext;
	
	private final Client client;
	
	private final String  accountManager;

	public EmailBodyContext(final ProcessContext kcontext, final Client client, final String accountManager) { 
		this.kcontext = kcontext;
		this.client = client;
		this.accountManager = accountManager;
	}

	public ProcessContext getKcontext() {
		return kcontext;
	}
	
	public Client getClient() {
		return client;
	}
	
	public String getAccountManager() {
		return accountManager;
	}

}
