package br.com.rafaelvieira.productapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST full API microservice (Ms-Sales) with Java 11 and Spring Boot 3")
                        .version("v1")
                        .description("API para orquestração de livros")
                        .termsOfService("https://github.com/rafaelrok/ms-sales")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://github.com/rafaelrok/ms-sales/blob/main/LICENSE")
                        )
                );
    }

}
