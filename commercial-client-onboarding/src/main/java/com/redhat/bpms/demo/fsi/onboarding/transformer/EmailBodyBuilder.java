package com.redhat.bpms.demo.fsi.onboarding.transformer;

import com.redhat.bpms.demo.fsi.onboarding.model.EmailBodyContext;

/**
 * Builds an email body from the given context.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class EmailBodyBuilder {

	//public static String buildEmailBody(EmailBodyContext bodyContext) {

	public static String buildEmailBody(EmailBodyContext bodyContext) {
		System.out.println("Hooray, we're building an e-mail body!!!!");
		/*
		return "This is the e-mail body. ProcessInstanceId: '" + bodyContext.getKcontext().getProcessInstance().getId() 
				+ "' for client: '" + bodyContext.getClient().getName() + "'.";
		*/
		
		return "This is the email body for process-id: '" + bodyContext.getKcontext().getProcessInstance().getId() + "' and client: '" 
				+ bodyContext.getClient().getName() + "'.";
	
	}

}
