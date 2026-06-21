package com.divvision33.wordfinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI wordFinderOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Word Finder API")
                .description("Finds and scores dictionary words that can be constructed from supplied letters.")
                .version("v1"));
    }
}
