# Massive Process Instance Abort with Spring and Java API

This Demo implements different methods for Aborting Processes and Cleaning the Logs Data.

###### In the ProcessInstance Class three abort methods have been implemented:

- **abortProcessInstance:** Aborts a Process Instance to the id passed as a parameter. 
- **abortProcessInstancebyProcID:** Aborts all instances corresponding to a Process. This is done by taking the ProcessID as a parameter.
- **abortProcessInstancebyDepID:** Aborts all instances corresponding to a Deployment. This is done by taking the DeploymentID as a parameter.

###### In the Cleanup Class two methods have been implemented to remove the Logs data:

- **cleanProc:** Cleans up all the log Data related to a ProcessID.
- **cleanDep:**  Cleans up all the log Data related to a DeploymentID.

###### There’s another set of methods that are included in order to facilitate testing. 

## Usage

To launch the application include your kjar and  invoke 

```bash
./launch.sh clean install
```

To facilitate testing swagger documentation has been included in the POC. To access it go to:

```
http://localhost:8090/swagger-ui.html
```

To verify the SQL statements that are ran, add the following to application.properties

```
spring.jpa.properties.hibernate.show_sql=true
logging.level.org.hibernate.type=trace
```






