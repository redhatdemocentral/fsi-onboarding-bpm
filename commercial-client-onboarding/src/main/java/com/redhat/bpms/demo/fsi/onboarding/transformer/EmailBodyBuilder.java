package com.redhat.bpms.demo.fsi.onboarding.transformer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.redhat.bpms.demo.fsi.onboarding.config.Environment;
import com.redhat.bpms.demo.fsi.onboarding.config.EnvironmentProducer;
import com.redhat.bpms.demo.fsi.onboarding.model.EmailBodyContext;
import com.redhat.bpms.demo.fsi.onboarding.model.Party;

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
	
	private static final Environment environment = EnvironmentProducer.getEnvironment();
	
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
			Party party = bodyContext.getClient().getRelatedParties().stream().findFirst().get().getParty();
			String name = party.getName();
			String surname = party.getSurname();
			String email = party.getEmail();
			
			Map<String, Object> mapper = getFreemarkerPlaceholderValues(processInstanceId, parentProcessInstanceId,
					accountManager, client,  buildUrl(parentProcessInstanceId, name, surname, client, email));
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

	private static Map<String, Object> getFreemarkerPlaceholderValues(long processInstanceId, long parentProcessInstanceId, String accountManager, String clientName, String entandoUrl) {
		Map<String, Object> map = new HashMap<>();
		map.put("processInstanceId", String.valueOf(processInstanceId));
		map.put("parentProcessInstanceId", String.valueOf(parentProcessInstanceId));
		map.put("accoutManager", accountManager);
		map.put("client", clientName);
		map.put("url", entandoUrl);
		return map;
	}
	
	private static String buildUrl(long id, String name, String surname, String company, String email) throws UnsupportedEncodingException {
		return environment.getEntandoBaseUrl() + "en/applicant.page?pid=" + id + "&fname=" + URLEncoder.encode(name, "UTF-8") + "&lame=" + URLEncoder.encode(surname, "UTF-8") + "&company=" + URLEncoder.encode(company, "UTF-8") + "&email=" + URLEncoder.encode(email, "UTF-8");
	}

}
