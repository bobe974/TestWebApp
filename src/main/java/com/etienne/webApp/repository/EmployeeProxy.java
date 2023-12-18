package com.etienne.webApp.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.etienne.webApp.AppWebApplication;
import com.etienne.webApp.CustomProperties;
import com.etienne.webApp.modele.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmployeeProxy {

    @Autowired
    private CustomProperties props;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EmployeeProxy.class);

    /**
    * Get all employees
    * @return An iterable of all employees
    */

    public Iterable<Employee> getEmployees() {
        String baseApiUrl = props.getApiUrl();
        String getEmployeesUrl = baseApiUrl + "/employees";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Employee>> response = restTemplate.exchange(
                getEmployeesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Employee>>() {}
                );

         logger.info("Get Employees call " + response.getStatusCode().toString());
         Iterable<Employee> employees =response.getBody();
         
         for(Employee e : employees) {
        	 logger.info(e.getFirstName());
         }
        
        return response.getBody();
    }
    
    public Employee getEmployee(int id) {
    	String baseApiUrl = props.getApiUrl();
        String getEmployeeUrl = baseApiUrl + "/employee/" + id;
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Employee> response = restTemplate.exchange(
        		getEmployeeUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Employee>() {}
                );

        logger.info("get employee + id call: " + response.getStatusCode().toString());
        logger.info(("Reponse:" + response.getBody().getFirstName()));
        
        return response.getBody();
    }
    
    public Employee createEmployee(Employee e) {
        String baseApiUrl = props.getApiUrl();
        String createEmployeeUrl = baseApiUrl + "/employee";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Employee> request = new HttpEntity<Employee>(e);
        ResponseEntity<Employee> response = restTemplate.exchange(
            createEmployeeUrl,
            HttpMethod.POST,
            request,
            Employee.class);
        
        logger.info("envoie de " + e.getFirstName());

        logger.info("Create Employee call " + response.getStatusCode().toString());
        
        logger.info("retour: "+ response.getBody().getLastName());

        return response.getBody();
    }
    
    /**
	 * Update an employee - using the PUT HTTP Method.
	 * @param e Existing employee to update
	 */
	public Employee updateEmployee(Employee e) {
		String baseApiUrl = props.getApiUrl();
		String updateEmployeeUrl = baseApiUrl + "/employee/" + e.getId();

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Employee> request = new HttpEntity<Employee>(e);
		ResponseEntity<Employee> response = restTemplate.exchange(
				updateEmployeeUrl, 
				HttpMethod.PUT, 
				request, 
				Employee.class);
		
		logger.debug("Update Employee call " + response.getStatusCode().toString());
		
		return response.getBody();
	}
	
	/**
	 * Delete an employee using exchange method of RestTemplate
	 * instead of delete method in order to log the response status code.
	 * @param e The employee to delete
	 */
	public void deleteEmployee(int id) {
		String baseApiUrl = props.getApiUrl();
		String deleteEmployeeUrl = baseApiUrl + "/employee/" + id;
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Void> response = restTemplate.exchange(
				deleteEmployeeUrl, 
				HttpMethod.DELETE, 
				null, 
				Void.class);
		
		logger.debug("Delete Employee call " + response.getStatusCode().toString());
	}
}