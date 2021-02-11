package com.poc.service;

import javax.persistence.EntityManagerFactory;

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

    @RequestMapping(value = "/byprocid", method = RequestMethod.POST)
    public String cleanProc(@RequestParam String ProcessId) {

        JPAAuditLogService auditService = new JPAAuditLogService(entityManagerFactory);
        auditService.variableInstanceLogDelete().processId(ProcessId).build().execute();
        auditService.variableInstanceLogDelete().processId(ProcessId).build().execute();


        TaskJPAAuditService TaskauditService = new TaskJPAAuditService(entityManagerFactory);
        TaskauditService.auditTaskDelete().processId(ProcessId).build().execute();
        TaskauditService.taskEventInstanceLogDelete().build().execute();
        TaskauditService.nodeInstanceLogDelete().build().execute();


          ExecutorJPAAuditService ExecauditService = new ExecutorJPAAuditService(entityManagerFactory);
          ErrorInfoDeleteBuilder ExecupdateBuilder = ExecauditService.errorInfoLogDeleteBuilder();
          ExecupdateBuilder.build().execute();

          RequestInfoLogDeleteBuilder RequpdateBuilder = ExecauditService.requestInfoLogDeleteBuilder();
          RequpdateBuilder.build().execute();

          auditService.processInstanceLogDelete().processId(ProcessId).build().execute();
        return "cleanup completed";

    }
}