package com.redhat.bpms.demo.fsi.onboarding.transformer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.redhat.bpms.demo.fsi.onboarding.model.EmailBodyContext;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * Builds an email body from the given context.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class EmailBodyBuilder {

	private static final Configuration cfg = getFreemarkerConfiguration();

	private static final String EMAIL_TEMPLATE_NAME = "email-template.ftl";
	
	private static final String ENTANDO_URL_PROPERTY_NAME = "fsi.demo.entando.customer.url";
	
	private static final String BASE_URL = System.getProperty(ENTANDO_URL_PROPERTY_NAME, "http://fsi-customer.serv.run/fsi-customer/");

	public static String buildEmailBody(EmailBodyContext bodyContext) {
		Template template;
		try {
			template = cfg.getTemplate(EMAIL_TEMPLATE_NAME);
		} catch (IOException ioe) {
			String message = "Unable to load e-mail template.";
			throw new RuntimeException(message, ioe);
		}

		StringWriter writer = new StringWriter();
		try {
			
			long parentProcessInstanceId = bodyContext.getKcontext().getProcessInstance().getParentProcessInstanceId(); 
			long processInstanceId = bodyContext.getKcontext().getProcessInstance().getId();
			String accountManager = bodyContext.getAccountManager();
			String client = bodyContext.getClient().getName();
			
			Map<String, Object> mapper = getFreemarkerPlaceholderValues(processInstanceId, parentProcessInstanceId,
					accountManager, client);
			template.process(mapper, writer);
		} catch (TemplateException | IOException e) {
			String message = "Unable to transform email body.";
			throw new RuntimeException(message, e);

		}
		return writer.toString();
	}

	private static Configuration getFreemarkerConfiguration() {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(EmailBodyBuilder.class, "/");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		return cfg;
	}

	private static Map<String, Object> getFreemarkerPlaceholderValues(long processInstanceId, long parentProcessInstanceId, String accountManager, String clientName) {
		Map<String, Object> map = new HashMap<>();
		map.put("processInstanceId", String.valueOf(processInstanceId));
		map.put("parentProcessInstanceId", String.valueOf(parentProcessInstanceId));
		map.put("accoutManager", accountManager);
		map.put("client", clientName);
		map.put("url", buildUrl(parentProcessInstanceId));
		return map;
	}
	
	private static String buildUrl(long id) { 
		return BASE_URL + "en/applicant?pid=" + id;
	}

}
