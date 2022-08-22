package br.com.rafaelvieira.paymeapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * @author rafae
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST full (Payme-api) with Java 11 and Spring Boot 3")
                .description("API para controle de pagamento no checkout com integração a api de vendas")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/rafaelrok/ms-sales/blob/main/LICENSE")
                .contact(new Contact(
                        "Rafael Vieira",
                        "https://github.com/rafaelrok/ms-sales",
                        "rafaelrok25@gmail.com"))
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(getRequiredParameters())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    private List<Parameter> getRequiredParameters() {
        return Collections.singletonList(new ParameterBuilder()
                .name("Authorization")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build()
        );
    }
}
