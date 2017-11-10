package com.redhat.bpms.demo.fsi.onboarding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.process.core.impl.DataTransformerRegistry;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.internal.runtime.manager.context.EmptyContext;

import com.redhat.bpms.demo.fsi.onboarding.model.Client;
import com.redhat.bpms.demo.fsi.onboarding.model.Party;
import com.redhat.bpms.demo.fsi.onboarding.model.RelatedParty;

public class ClientOnboardingProcessTest extends JbpmJUnitBaseTestCase {

	private static final String PROCESS_ID = "commercial-client-onboarding.ClientOnboardingProcess";

	public ClientOnboardingProcessTest() {
		super(true, true);
	} 
	
	
	
	@Test
	public void testSendApplicationLink() {
		
		RuntimeManager runtimeManager = createRuntimeManager("com/redhat/bpms/demo/fsi/onboarding/ClientOnboardingProcess.bpmn2", 
				"com/redhat/bpms/demo/fsi/onboarding/SendApplicationLinkProcess.bpmn2",
				"com/redhat/bpms/demo/fsi/onboarding/KycDueDiligenceProcess.bpmn2",
				"com/redhat/bpms/demo/fsi/onboarding/LegalDocumentReviewProcess.bpmn2");
		RuntimeEngine runtimeEngine = runtimeManager.getRuntimeEngine(EmptyContext.get());
		
		KieSession kieSession = runtimeEngine.getKieSession();
		
		kieSession.getWorkItemManager().registerWorkItemHandler("Email", new MockEmailWorkItemHandler());
		
		
		
		Map<String, Object> params = new HashMap<>();
		Client client = getClient();
		params.put("client", client);

		kieSession.startProcess(PROCESS_ID, params);
		
		disposeRuntimeManager();
	}
	
	private void registerTransformers() {
		//No need to register custom transformer. We call our transformation logic via MVEL.
		DataTransformerRegistry transformerRegistry = DataTransformerRegistry.get();
		
		
		
		//transformerRegistry.register("email", new EmailBodyBuilder());
	}
	
	private Client getClient() {
		Client client = new Client();
		
		//Address clientAddress = new Address();
		
		client.setBic("123456");
		client.setCountry("NL");
		client.setName("NIX");
		client.setPhoneNumber("0612345678");
		client.setType("BIG_BUSINESS");
		
		Party party = new Party();
		party.setDateOfBirth(new Date());
		party.setEmail("ddoyle@redhat.com");
		party.setName("Duncan");
		party.setSurname("Doyle");
		party.setSsn("123456789");
		
		RelatedParty rp = new RelatedParty();
		rp.setParty(party);
		rp.setRelationship("Consultant");
		
		List<RelatedParty> relatedParties = client.getRelatedParties();
		if (relatedParties == null) {
			relatedParties = new ArrayList<>();
		}
		relatedParties.add(rp);
		client.setRelatedParties(relatedParties);
		
		return client;
	}
	
	
}
