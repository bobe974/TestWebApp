package com.etienne.webApp.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.etienne.webApp.AppWebApplication;
import com.etienne.webApp.CustomProperties;
import com.etienne.webApp.modele.Employe;

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

    public Iterable<Employe> getEmployees() {
        String baseApiUrl = props.getApiUrl();
        String getEmployeesUrl = baseApiUrl + "/employees";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Employe>> response = restTemplate.exchange(
                getEmployeesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Employe>>() {}
                );

         logger.info("Get Employees call " + response.getStatusCode().toString());
         Iterable<Employe> employees =response.getBody();
         
         for(Employe e : employees) {
        	 logger.info(e.getFirstName());
         }
        
        return response.getBody();
    }

}