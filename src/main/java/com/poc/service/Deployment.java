package com.poc.service;

import java.util.ArrayList;
import java.util.Collection;
import io.swagger.v3.oas.annotations.Operation;


import org.jbpm.services.api.DeploymentService;
import org.jbpm.services.api.model.DeployedUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.jbpm.services.api.model.DeploymentUnit;
import org.jbpm.kie.services.impl.CustomIdKModuleDeploymentUnit;

@RestController
@RequestMapping("/deployment")
public class Deployment {
	
	@Autowired
	private DeploymentService deploymentService;

	@Operation(summary = "List deployments", description = "List all deployments", tags = { "Deployment" })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Collection<String> deployShow() {
    	Collection<DeployedUnit> deployed = deploymentService.getDeployedUnits();
    	Collection<String> units = new ArrayList<String>();
    	
    	for (DeployedUnit dUnit : deployed) {
    		units.add(dUnit.getDeploymentUnit().getIdentifier());
    	}
    	
        return units;
    }
    @Operation(summary = "Deploy a container", description = "Deploy a container", tags = { "Deployment" })
    @RequestMapping(value="/deploy", method=RequestMethod.POST)
    public String deploy(@RequestParam("containerId")String containerId, @RequestParam("groupID") String groupID, @RequestParam("arifactID") String arifactID, @RequestParam("version") String version) {
    	String outcome = "Deployment " + containerId + " deployed successfully";
    	
		DeploymentUnit unit = new CustomIdKModuleDeploymentUnit( containerId , groupID, arifactID, version);

		deploymentService.deploy(unit);
    	
    	return outcome;
	}
	
    @Operation(summary = "Undeploy a container", description = "Undeploy a container", tags = { "Deployment" })
    @RequestMapping(value="/undeploy", method=RequestMethod.POST)
    public String undeploy(@RequestParam("id")String id) {
    	String outcome = "";
    	DeployedUnit deployed = deploymentService.getDeployedUnit(id);
    	if (deployed != null) {
    		deploymentService.undeploy(deployed.getDeploymentUnit());
    		outcome = "Deployment " + id + " undeployed successfully";
    	} else {
    		outcome = "No deployment " + id + " found";
    	}
    	return outcome;
    }
}