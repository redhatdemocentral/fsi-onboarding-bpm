
/*
	 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
	 *
	 * Licensed under the Apache License, Version 2.0 (the "License");
	 * you may not use this file except in compliance with the License.
	 *
	 *      http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * Unless required by applicable law or agreed to in writing, software
	 * distributed under the License is distributed on an "AS IS" BASIS,
	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	 * See the License for the specific language governing permissions and
	 * limitations under the License.
	*/

package org.kie.server.services.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.jbpm.kie.services.impl.query.SqlQueryDefinition;
import org.jbpm.services.api.query.QueryService;
import org.jbpm.services.api.query.model.QueryDefinition;
import org.jbpm.services.api.query.model.QueryDefinition.Target;
import org.kie.server.api.KieServerConstants;
import org.kie.server.services.api.KieContainerInstance;
import org.kie.server.services.api.KieServerApplicationComponentsService;
import org.kie.server.services.api.KieServerExtension;
import org.kie.server.services.api.KieServerRegistry;
import org.kie.server.services.api.SupportedTransports;
import org.kie.server.services.impl.KieServerImpl;
import org.kie.server.services.jbpm.JbpmKieServerExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryRegistrationExtension implements KieServerExtension {

	public static final String EXTENSION_NAME = "QueryRegistration";

	private static final Logger logger = LoggerFactory.getLogger(QueryRegistrationExtension.class);

	private static final Boolean disabled = Boolean
			.parseBoolean(System.getProperty("org.jbpm.query.registration.server.ext.disabled", "false"));
	private static final Boolean jbpmDisabled = Boolean
			.parseBoolean(System.getProperty(KieServerConstants.KIE_JBPM_SERVER_EXT_DISABLED, "false"));

	private KieServerImpl kieServer;
	private KieServerRegistry context;

	private QueryService queryService;
	
	private boolean initialized = false;
	
	//private List<Object> services = new ArrayList<Object>();
	
	
	@Override
	public boolean isActive() {
		return disabled == false && jbpmDisabled == false;
	}

	@Override
	public void init(KieServerImpl kieServer, KieServerRegistry registry) {
		this.kieServer = kieServer;
		this.context = registry;

		// Requires jBPM Extension, as we depend on the QueryService.
		KieServerExtension jBpmExtension = registry.getServerExtension(JbpmKieServerExtension.EXTENSION_NAME);
		if (jBpmExtension == null) {
			logger.error("No jBPM extension available, quiting...");
			return;
		}
		queryService = jBpmExtension.getAppComponents(QueryService.class);

		logger.info("Registering custom queries.");
		//Register our queries.
		QueryDefinition queryDef = new SqlQueryDefinition("getProcessInstancesWithVars", "java:jboss/datasources/ExampleDS", Target.PROCESS);
		queryDef.setExpression("select pil.*, v.variableId, v.value from ProcessInstanceLog pil INNER JOIN VariableInstanceLog v ON (v.processInstanceId = pil.processInstanceId)INNER JOIN (select vil.processInstanceId ,vil.variableId, MAX(vil.ID) maxvilid  FROM VariableInstanceLog vil GROUP BY vil.processInstanceId, vil.variableId ORDER BY vil.processInstanceId)  x ON (v.variableId = x.variableId  AND v.id = x.maxvilid )");
		queryService.replaceQuery(queryDef);
		
		QueryDefinition queryDefTwo = new SqlQueryDefinition("getProcessInstancesWithClient", "java:jboss/datasources/ExampleDS", Target.PROCESS);
		//queryDefTwo.setExpression("select pil.*, c.id as clientId, c.name, c.bic, c.type from ProcessInstanceLog pil inner join (select mv.map_var_id, mv.processinstanceid from MappedVariable mv) mv on (mv.processinstanceid = pil.id) inner join Client c on (c.id = mv.map_var_id)");
		queryDefTwo.setExpression("select pil.*, c.id as clientId, c.name, c.bic, c.type, c.country, rel.relationship, par.email, par.dateofbirth, par.name as pname, par.surname, par.ssn from ProcessInstanceLog pil inner join (select mv.map_var_id, mv.processinstanceid from MappedVariable mv) mv on (mv.processinstanceid = pil.id) inner join Client c on (c.id = mv.map_var_id) inner join client_relatedparty cr on (c.id=cr.client_id) inner join relatedparty rel on (cr.relatedparties_id=rel.id) inner join party par on (rel.party_id=par.id)");
		queryService.replaceQuery(queryDefTwo);
		
		initialized = true;
	}

	

	@Override
	public void destroy(KieServerImpl kieServer, KieServerRegistry registry) {
		// no-op
	}

	@Override
	public void createContainer(String id, KieContainerInstance kieContainerInstance, Map<String, Object> parameters) {
		// no-op
	}

	@Override
	public boolean isUpdateContainerAllowed(String id, KieContainerInstance kieContainerInstance, Map<String, Object> parameters) {
		return true;
	}

	@Override
	public void updateContainer(String id, KieContainerInstance kieContainerInstance, Map<String, Object> parameters) {
		// no-op
	}

	@Override
	public void disposeContainer(String id, KieContainerInstance kieContainerInstance, Map<String, Object> parameters) {
		// no-op
	}

	@Override
	public List<Object> getAppComponents(SupportedTransports type) {

		ServiceLoader<KieServerApplicationComponentsService> appComponentsServices = ServiceLoader
				.load(KieServerApplicationComponentsService.class);
		List<Object> appComponentsList = new ArrayList<Object>();
		Object[] services = { context };
		for (KieServerApplicationComponentsService appComponentsService : appComponentsServices) {
			appComponentsList.addAll(appComponentsService.getAppComponents(EXTENSION_NAME, type, services));
		}
		return appComponentsList;
	}

	@Override
	public <T> T getAppComponents(Class<T> serviceType) {
		return null;
	}

	@Override
	public String getImplementedCapability() {
		return "QUERY_REGISTRATION";
	}

	@Override
	public List<Object> getServices() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public String getExtensionName() {
		return EXTENSION_NAME;
	}

	@Override
	public Integer getStartOrder() {
		return Integer.MAX_VALUE;
	}

	@Override
	public String toString() {
		return EXTENSION_NAME + " KIE Server extension";
	}

}
