package br.com.rafaelvieira.productapi.config;

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

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST full API microservice (Ms-Sales) with Java 11 and Spring Boot 3")
                .description("API para orquestração de livros")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/rafaelrok/ms-sales/blob/main/LICENSE")
                        .contact(new Contact(
                                "Rafael Vieira",
                                "https://github.com/rafaelrok/ms-sales",
                                "rafaelrok25@gmail.com"))
                        .build();
    }

//    private ApiInfo getApiInfo() {
//        return new ApiInfoBuilder()
//                .title(appName)
//                .description(appDescription)
//                .version(appVersion)
//                .licenseUrl(appUrl)
//                .build();
//    }


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
