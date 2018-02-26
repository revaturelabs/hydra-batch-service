package com.revature.caliber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.revature.caliber.model.SimpleBatch;
import com.revature.caliber.service.BatchCompositionMessageService;
import com.revature.caliber.service.BatchCompositionService;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient 
public class BatchRepositoryServiceApplication {
//	@Autowired
//	BatchCompositionMessageService bcms;
	@Autowired
	BatchCompositionService bcs;
	public static void main(String[] args) {
		SpringApplication.run(BatchRepositoryServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner runner() {
		return args -> {
			System.out.println(bcs.findAllCurrent());
		};
	}
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.revature.caliber.controller"))              
          .paths(PathSelectors.any())                          
          .build();
    }
	
}

