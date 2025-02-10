package com.jeanpires.objetivobackmetaway.core.springDoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("MetaWay API")
                        .version("v1")
                        .description("REST API de case para MetaWay")
                        .license(new License().name("Apache 2.0").url("http://springdoc.com")))
                .addSecurityItem(new SecurityRequirement().addList("security_auth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("security_auth", new SecurityScheme()
                                .type(SecurityScheme.Type.OAUTH2)
                                .flows(new io.swagger.v3.oas.models.security.OAuthFlows()
                                        .password(new io.swagger.v3.oas.models.security.OAuthFlow()
                                                .tokenUrl("http://localhost:8080/oauth/token")))));
    }
}
