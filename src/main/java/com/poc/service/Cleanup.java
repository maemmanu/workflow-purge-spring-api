package com.poc.service;

import javax.persistence.EntityManagerFactory;
import io.swagger.v3.oas.annotations.Operation;

import org.jbpm.executor.impl.jpa.ExecutorJPAAuditService;
import org.kie.internal.runtime.manager.audit.query.ErrorInfoDeleteBuilder;
import org.kie.internal.runtime.manager.audit.query.RequestInfoLogDeleteBuilder;
import org.jbpm.process.audit.JPAAuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.jbpm.services.task.audit.service.TaskJPAAuditService;

@RestController
@RequestMapping("/logcleanup")
public class Cleanup {
	
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Operation(summary = "Cleanup by ProcessID", description = "Cleanup all log data related to ProcessID", tags = { "LogCleanup" })
    @RequestMapping(value = "/byprocid", method = RequestMethod.POST)
    public String cleanProc(@RequestParam String ProcessId) {

        JPAAuditLogService auditService = new JPAAuditLogService(entityManagerFactory);
        auditService.variableInstanceLogDelete().processId(ProcessId).build().execute();
        auditService.nodeInstanceLogDelete().processId(ProcessId).build().execute();


        TaskJPAAuditService TaskauditService = new TaskJPAAuditService(entityManagerFactory);
        TaskauditService.auditTaskDelete().processId(ProcessId).build().execute();
        TaskauditService.taskEventInstanceLogDelete().build().execute();
        TaskauditService.taskVariableInstanceLogDelete().build().execute();


          ExecutorJPAAuditService ExecauditService = new ExecutorJPAAuditService(entityManagerFactory);
          ErrorInfoDeleteBuilder ExecupdateBuilder = ExecauditService.errorInfoLogDeleteBuilder();
          ExecupdateBuilder.build().execute();

          RequestInfoLogDeleteBuilder RequpdateBuilder = ExecauditService.requestInfoLogDeleteBuilder();
          RequpdateBuilder.build().execute();

          auditService.processInstanceLogDelete().processId(ProcessId).build().execute();
        return "cleanup completed";

    }

    @Operation(summary = "Cleanup by DeploymentID", description = "Cleanup all log data related to DeploymentID", tags = { "LogCleanup" })
    @RequestMapping(value = "/bydepid", method = RequestMethod.POST)
    public String cleanDep(@RequestParam String DeploymentId) {

        JPAAuditLogService auditService = new JPAAuditLogService(entityManagerFactory);
        auditService.variableInstanceLogDelete().externalId(DeploymentId).build().execute();
        auditService.nodeInstanceLogDelete().externalId(DeploymentId).build().execute();


        TaskJPAAuditService TaskauditService = new TaskJPAAuditService(entityManagerFactory);
        TaskauditService.auditTaskDelete().deploymentId(DeploymentId).build().execute();
        TaskauditService.taskEventInstanceLogDelete().build().execute();
        TaskauditService.taskVariableInstanceLogDelete().build().execute();


          ExecutorJPAAuditService ExecauditService = new ExecutorJPAAuditService(entityManagerFactory);
          ErrorInfoDeleteBuilder ExecupdateBuilder = ExecauditService.errorInfoLogDeleteBuilder();
          ExecupdateBuilder.build().execute();

          RequestInfoLogDeleteBuilder RequpdateBuilder = ExecauditService.requestInfoLogDeleteBuilder();
          RequpdateBuilder.build().execute();

          auditService.processInstanceLogDelete().externalId(DeploymentId).build().execute();
        return "cleanup completed";

    }
}