package com.deploy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "DEPLOYMENT OF MICRO SERVICES OF SPRING BOOT APPS"))
public class GuruDeployApplication {
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    public static void main(String[] args) {
        
        SpringApplication.run(GuruDeployApplication.class, args);

    }

}
