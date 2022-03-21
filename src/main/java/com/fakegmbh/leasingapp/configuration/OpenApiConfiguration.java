package com.fakegmbh.leasingapp.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OpenApiConfiguration {

    private static final String APP_PACKAGE = "com.fakegmbh.leasingapp";
    private static final String API_VERSION = "1.0.0";
    private static final String API_DESCRIPTION = "API for administrating leasing contracts.";
    private static final String API_TITLE = "Leasing App's API";

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(new Info().title(API_TITLE)
                                            .description(API_DESCRIPTION)
                                            .version(API_VERSION));
    }
}