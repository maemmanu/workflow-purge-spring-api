package com.poc.service;

import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;

import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.model.ProcessInstanceDesc;
import org.kie.internal.query.QueryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processinstance")
public class ProcessInstance {
	
	@Autowired
	private RuntimeDataService runtimeDataService;
	
	@Autowired
	private ProcessService processService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Collection<ProcessInstanceDesc> getProcessInstances() {
		
		Collection<ProcessInstanceDesc> processInstances = runtimeDataService.getProcessInstances(new QueryContext(0, 100));

		return processInstances;
 
	}
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ProcessInstanceDesc getProcessInstance(@RequestParam String id) {
		long processInstanceId = Long.parseLong(id);
		ProcessInstanceDesc processInstance = runtimeDataService.getProcessInstanceById(processInstanceId);

		return processInstance;
	}
	
	@RequestMapping(value = "/abortbyid", method = RequestMethod.POST)
	public String abortProcessInstance(@RequestParam String id) {
		
		processService.abortProcessInstance(Long.parseLong(id));
		
		return "Instance (" + id + ") aborted successfully";
	}

	@RequestMapping(value = "/abortbyprocessid", method = RequestMethod.POST)
	public String abortProcessInstancebyProcID(@RequestParam String ProcessId) {

		Collection<ProcessInstanceDesc> processInstanceIds = runtimeDataService.getProcessInstancesByProcessId(Arrays.asList(1), ProcessId, "anonymousUser", new QueryContext(0, 100) );
		
		for (Iterator iterator = processInstanceIds.iterator(); iterator.hasNext();) {
			ProcessInstanceDesc processInstanceDesc = (ProcessInstanceDesc) iterator.next();
			processService.abortProcessInstance(processInstanceDesc.getId());
		}
		return "All Instances of Process (" + ProcessId + ") aborted successfully";
		
	}

	@RequestMapping(value = "/abortbydeploymentid", method = RequestMethod.POST)
	public String abortProcessInstancebyDepID(@RequestParam String DeploymentId) {

		Collection<ProcessInstanceDesc> processInstanceIds = runtimeDataService.getProcessInstancesByDeploymentId(DeploymentId, Arrays.asList(1), new QueryContext(0, 100) );
		
		for (Iterator iterator = processInstanceIds.iterator(); iterator.hasNext();) {
			ProcessInstanceDesc processInstanceDesc = (ProcessInstanceDesc) iterator.next();
			processService.abortProcessInstance(processInstanceDesc.getId());
		}
		return "All Instances of Deployment (" + DeploymentId + ") aborted successfully";
		
	}
	
}