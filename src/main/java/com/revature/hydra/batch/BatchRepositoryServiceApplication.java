package com.revature.hydra.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.revature.hydra.batch.service.BatchCompositionService;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EntityScan("com.revature.beans")
public class BatchRepositoryServiceApplication {
//	@Autowired
//	BatchCompositionMessageService bcms;
	@Autowired
	BatchCompositionService bcs;
	public static void main(String[] args) {
		SpringApplication.run(BatchRepositoryServiceApplication.class, args);
	}
//	@Bean
//	public CommandLineRunner runner() {
//		return args -> {
//			System.out.println(bcs.findAllCurrent());
//		};
//	}
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.revature.hydra.controller"))              
          .paths(PathSelectors.any())                          
          .build();
    }
	
}

