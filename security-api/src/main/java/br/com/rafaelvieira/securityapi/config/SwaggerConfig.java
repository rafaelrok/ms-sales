package br.com.rafaelvieira.securityapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
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
    @Value("${app.client.id}")
    private String clientId;
    @Value("${app.client.secret}")
    private String clientSecret;
    @Value("${host.full.dns.auth.link}")
    private String authLink;


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

    private OAuth securitySchema() {
        List<AuthorizationScope> authorizationScopeList = new ArrayList();
        authorizationScopeList.add(new AuthorizationScope("read", "Read all"));
        authorizationScopeList.add(new AuthorizationScope("write", "Access All"));
        List<GrantType> grantTypes = new ArrayList();
        GrantType creGrant = new
                ResourceOwnerPasswordCredentialsGrant(authLink+"/oauth/token");
        grantTypes.add(creGrant);
        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);
    }
    private SecurityContext securityContext() {
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/api/users/**"))
                .build();
    }
    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[2];
        authorizationScopes[0] = new AuthorizationScope("read", "Read all");
        authorizationScopes[1] = new AuthorizationScope("write", "Access All");
        return Collections.singletonList(new SecurityReference("oauth2schema",
                authorizationScopes));
    }
    @Bean
    public SecurityConfiguration securityInfo() {
        return SecurityConfigurationBuilder.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(true)
                .build();
    }

}
