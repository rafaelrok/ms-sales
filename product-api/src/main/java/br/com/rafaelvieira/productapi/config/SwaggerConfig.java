package br.com.rafaelvieira.productapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

/**
 * @author rafae
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String AUTHORIZATION = "Authorization";
    private static final String TRANSACTION_ID = "transactionid";

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST full API (product-api) with Java 11 and Spring Boot 3")
                .description("API para controle de estoque de produtos com integração a api de vendas")
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
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", AUTHORIZATION, "header");
                //new ApiKey("transactionid", TRANSACTION_ID, "header");

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .globalOperationParameters(getRequiredParameters())
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private List<Parameter> getRequiredParameters() {
//        return Collections.singletonList(new ParameterBuilder()
//                .name("Authorization")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false)
//                .build()
//        );
//    }
}
