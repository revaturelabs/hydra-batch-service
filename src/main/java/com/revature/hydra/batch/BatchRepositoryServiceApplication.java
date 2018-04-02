package com.revature.hydra.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient 
@ComponentScan
public class BatchRepositoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchRepositoryServiceApplication.class, args);
	}

}
