package com.example.sitework;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class SiteworkBackendApplication {
 
	
	public static void main(String[] args) {

		
		Runtime r = Runtime.getRuntime();
	

		ApplicationContext context =  
				SpringApplication.run(SiteworkBackendApplication.class, args);
	
	}
	
	 @Bean
	    public OpenAPI openApiConfig() {
	        return new OpenAPI().info(apiInfo());
	    }

	    public Info apiInfo() {
	        Info info = new Info();
	        info
	                .title("Site Work API")
	                .description("handle Site work order header and detailed order ")
	                .version("v1.0.0");
	        return info;
	    }

 
}