package com.redhat.bpms.demo.fsi.onboarding;

import java.util.Map;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

public class MockEmailWorkItemHandler implements WorkItemHandler {

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		Map<String, Object> params = workItem.getParameters();
		
		String body = (String) params.get("Body");
		System.out.println(body);
		
		//Complete the WIH
		manager.completeWorkItem(workItem.getId(), null);
	}

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		throw new UnsupportedOperationException();
	}

}
