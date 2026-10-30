package com.joao.empresa.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI maintenanceManagementOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title(
                                        "Maintenance Management API"
                                )
                                .description(
                                        "API REST para gerenciamento "
                                                + "de empresas, equipamentos, "
                                                + "usuários e manutenções."
                                )
                                .version("1.0.0")
                );
    }
}
