package com.poc.service;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.jbpm.process.audit.JPAAuditLogService;
import org.kie.internal.runtime.manager.audit.query.ProcessInstanceLogDeleteBuilder;
import org.kie.api.runtime.process.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/logcleanup")
public class Cleanup {
	
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @RequestMapping(value = "/clean", method = RequestMethod.POST)
    public String cleanProc(@RequestParam String ProcessName) {

        //EntityManagerFactory emf = entityManagerFactory.getOrCreate("org.hibernate.jpa.HibernatePersistenceProvider");
        JPAAuditLogService auditService = new JPAAuditLogService(entityManagerFactory);
        ProcessInstanceLogDeleteBuilder updateBuilder = auditService.processInstanceLogDelete().processName(ProcessName);
        int result = updateBuilder.build().execute();
        return "complete";
    }
}