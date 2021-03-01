package com.poc.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import io.swagger.v3.oas.annotations.Operation;

import org.jbpm.services.api.admin.ProcessInstanceAdminService;
import org.jbpm.services.api.RuntimeDataService;
import org.jbpm.services.api.model.NodeInstanceDesc;
import org.kie.internal.query.QueryContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/node")
public class CancelNode {
	
	@Autowired
	private ProcessInstanceAdminService processInstanceAdminService;

	@Operation(summary = "Show active node instances", description = "Show all active node instances", tags = { "NodeInstance" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Collection<NodeInstanceDesc> getActiveNodeInstance(@RequestParam String id) {
        
        long processInstanceId = Long.parseLong(id);
		Collection<NodeInstanceDesc> activeNodeInstances = processInstanceAdminService.getActiveNodeInstances(processInstanceId);

		return activeNodeInstances;
    }

    @Operation(summary = "Cancel active node instances", description = "Cancel active node instances", tags = { "NodeInstance" })
	@RequestMapping(value = "/cancelnode", method = RequestMethod.POST)
	public String CancelNodeInstance(@RequestParam String id) {

        long processInstanceId = Long.parseLong(id);
        Collection<NodeInstanceDesc> activeNodeInstances = processInstanceAdminService.getActiveNodeInstances(processInstanceId);
        for (Iterator iterator = activeNodeInstances.iterator(); iterator.hasNext();) {
            NodeInstanceDesc nodeInstanceDesc = (NodeInstanceDesc) iterator.next();
		processInstanceAdminService.cancelNodeInstance(processInstanceId, nodeInstanceDesc.getId());

        }
		return "node cancelled";
		
	}
	
}