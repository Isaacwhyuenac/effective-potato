package com.example.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(
  title = "My App",
  description = "Some long and useful description", version = "v1")
)
@SecurityScheme(
  name = "security_auth",
  type = SecuritySchemeType.OAUTH2,
  flows = @OAuthFlows(
    authorizationCode = @OAuthFlow(
      authorizationUrl = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}",
      tokenUrl = "${auth0.audience}",
      scopes = {
        @OAuthScope(name = "read", description = "read scope"),
        @OAuthScope(name = "write", description = "write scope")})
  )
)
public class SwaggerConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .components(new Components())
      .info(new Info().title("Order Service API").version("1.0.0")
        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }

}
