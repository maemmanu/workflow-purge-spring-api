import org.jbpm.services.task.admin.listener.TaskCleanUpProcessEventListener;
import org.kie.api.task.TaskService;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import java.util.Collection;
import java.util.Arrays;
import java.util.Iterator;
import io.swagger.v3.oas.annotations.Operation;
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

	@Operation(summary = "Show Process Instances", description = "Show all Process Instances", tags = { "ProcessInstances" })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Collection<ProcessInstanceDesc> getProcessInstances() {
		
		Collection<ProcessInstanceDesc> processInstances = runtimeDataService.getProcessInstances(new QueryContext(0, 1000));

		return processInstances;
 
	}

	@Operation(summary = "Show a Process Instances", description = "Show a specific Process Instances for a specific id", tags = { "ProcessInstances" })
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ProcessInstanceDesc getProcessInstance(@RequestParam String id) {
		long processInstanceId = Long.parseLong(id);
		ProcessInstanceDesc processInstance = runtimeDataService.getProcessInstanceById(processInstanceId);

		return processInstance;
	}
	
	@Operation(summary = "Abort an Instance", description = "Abort an instance corresponding to a specific Process Instances id", tags = { "AbortInstance" })
	@RequestMapping(value = "/abortbyid", method = RequestMethod.POST)
	public String abortProcessInstance(@RequestParam String id) {
		
		processService.abortProcessInstance(Long.parseLong(id));
		
		return "Instance (" + id + ") aborted successfully";
	}

	@Operation(summary = "Abort all Instances of a Process", description = "Abort all Instances of a Process", tags = { "AbortInstance" })
	@RequestMapping(value = "/abortbyprocessid", method = RequestMethod.POST)
	public String abortProcessInstancebyProcID(@RequestParam String ProcessId) {

		Collection<ProcessInstanceDesc> processInstanceIds = runtimeDataService.getProcessInstancesByProcessId(Arrays.asList(1), ProcessId, "anonymousUser", new QueryContext(0, 1000) );
		
		for (Iterator iterator = processInstanceIds.iterator(); iterator.hasNext();) {
			ProcessInstanceDesc processInstanceDesc = (ProcessInstanceDesc) iterator.next();
			processService.abortProcessInstance(processInstanceDesc.getId());
		}
		return "All Instances of Process (" + ProcessId + ") aborted successfully";
		
	}

	@Operation(summary = "Abort all Instances of a Deployment", description = "Abort all Instances corresponding to DeploymentID", tags = { "AbortInstance" })
	@RequestMapping(value = "/abortbydeploymentid", method = RequestMethod.POST)
	public String abortProcessInstancebyDepID(@RequestParam String DeploymentId) {

		Collection<ProcessInstanceDesc> processInstanceIds = runtimeDataService.getProcessInstancesByDeploymentId(DeploymentId, Arrays.asList(1), new QueryContext(0, 1000) );
		
		for (Iterator iterator = processInstanceIds.iterator(); iterator.hasNext();) {
			ProcessInstanceDesc processInstanceDesc = (ProcessInstanceDesc) iterator.next();
			processService.abortProcessInstance(processInstanceDesc.getId());
		}
		return "All Instances of Deployment (" + DeploymentId + ") aborted successfully";
		
	}
	
}