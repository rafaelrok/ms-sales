package br.com.rafaelvieira.productapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

/**
 * @author rafae
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${info.app.name}")
    private String appName;
    @Value("${info.app.description}")
    private String appDescription;
    @Value("${info.app.version}")
    private String appVersion;
    @Value("${info.app.license.name}")
    private String appNameLicense;
    @Value("${info.app.license.url}")
    private String appUrlLicense;
    @Value("${info.app.contact.name}")
    private String contactName;
    @Value("${info.app.contact.email}")
    private String contactEmail;
    @Value("${info.app.contact.url}")
    private String appUrl;


    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(appName)
                .description(appDescription)
                .version(appVersion)
                .license(appNameLicense)
                .licenseUrl(appUrlLicense)
                        .contact(new Contact(
                                contactName,
                                appUrl,
                                contactEmail))
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
