package com.etienne.webApp;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.etienne.webApp.repository.EmployeeProxy;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Data
@SpringBootApplication
public class AppWebApplication implements CommandLineRunner {

	@Autowired
	EmployeeProxy employeeProxy; 
	
	public static void main(String[] args) {
		SpringApplication.run(AppWebApplication.class, args);
	}

	@PostConstruct
    public void afterStartup() {
        // Code à exécuter après le démarrage de l'application
		org.slf4j.Logger logger = LoggerFactory.getLogger(AppWebApplication.class);
		logger.info("************POST**************************");
        employeeProxy.getEmployees(); // Appel de la méthode de EmployeeProxy
    }
	
    @Autowired
    private CustomProperties properties;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println(properties.getApiUrl());
		
		 org.slf4j.Logger logger = LoggerFactory.getLogger(AppWebApplication.class);
//		  logger.debug("**************************************");
//	       logger.info("**************************************");
//	       logger.error("**************************************");
		
	}

}
