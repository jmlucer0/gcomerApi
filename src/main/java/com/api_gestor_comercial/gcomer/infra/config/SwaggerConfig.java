package com.api_gestor_comercial.gcomer.infra.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {
    public OpenApiCustomizer openApiCustomiser() {
        return openApi -> openApi
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Gcomer")
                        .version("1.0")
                        .description("API documentation")
                );
    }
}
