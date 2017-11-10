package com.redhat.bpms.demo.fsi.onboarding.transformer;

import org.junit.Test;
import org.kie.api.definition.process.Process;
import org.kie.api.runtime.KieRuntime;
import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.ProcessContext;
import org.kie.api.runtime.process.ProcessInstance;

import com.redhat.bpms.demo.fsi.onboarding.model.Client;
import com.redhat.bpms.demo.fsi.onboarding.model.EmailBodyContext;

public class EmailBodyBuilderTest {

	
	@Test
	public void testBuildEmailBody() {
		
		
		ProcessContext pContext = new ProcessContext() {
			
			@Override
			public KieRuntime getKnowledgeRuntime() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public KieRuntime getKieRuntime() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setVariable(String variableName, Object value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getVariable(String variableName) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ProcessInstance getProcessInstance() {
				// TODO Auto-generated method stub
				return new ProcessInstance() {
					
					@Override
					public void signalEvent(String type, Object event) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public String[] getEventTypes() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public int getState() {
						// TODO Auto-generated method stub
						return 0;
					}
					
					@Override
					public String getProcessName() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public String getProcessId() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public Process getProcess() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public long getParentProcessInstanceId() {
						// TODO Auto-generated method stub
						return 1;
					}
					
					@Override
					public long getId() {
						// TODO Auto-generated method stub
						return 2;
					}
				};
			}
			
			@Override
			public NodeInstance getNodeInstance() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		Client client = new Client();
		client.setName("Cool Client");
		
		
		EmailBodyContext context = new EmailBodyContext(pContext, client, "prakash");
		
		String emailBody = EmailBodyBuilder.buildEmailBody(context);
		
		System.out.println(emailBody);
		
	}
	
	
}
