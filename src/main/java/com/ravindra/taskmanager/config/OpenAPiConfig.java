package com.ravindra.taskmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenAPiConfig {
    @Bean
    public OpenAPI taskManagerOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manager API")
                        .description("REST API for managing tasks build with Spring Boot")
                        .version("1.0.0"));
    }
}
