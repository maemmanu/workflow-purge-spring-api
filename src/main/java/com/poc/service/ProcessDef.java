package com.poc.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;

import org.jbpm.services.api.ProcessService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.model.ProcessDefinition;
import org.kie.internal.query.QueryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processdef")
public class ProcessDef {
	
	@Autowired
	private RuntimeDataService runtimeDataService;
	
	@Autowired
	private ProcessService processService;

	@Operation(summary = "Show all Process definitions", description = "Show all Process definitions", tags = { "ProcessDefinition" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Collection<ProcessDefinition> getProcessDef() {
		
		Collection<ProcessDefinition> processDefinitions = runtimeDataService.getProcesses(new QueryContext(0, 1000));

		return processDefinitions;
 
	}
	
	@Operation(summary = "Create a new Process Instance", description = "Create any number of instance of a process by specifiying the quantity", tags = { "ProcessInstances" })
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String newProcessInstance(@RequestParam String deploymentId, @RequestParam String processId,
	@RequestBody Map<String,String> allRequestParams, @RequestParam Integer quantity) {
		for (int i = 0; i < quantity ; i++) {
		long processInstanceId = processService.startProcess(deploymentId, processId, new HashMap<String, Object>(allRequestParams));
		String outcome = "ProcessInstance " + processInstanceId + "started";
		System.out.println(outcome);
		}
		return " " + quantity + " process instance created";
 
    }
}