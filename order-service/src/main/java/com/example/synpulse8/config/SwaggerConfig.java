package com.example.synpulse8.config;

import org.springframework.stereotype.Component;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Component
@EnableSwagger2WebMvc
public class SwaggerConfig {

  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Api")
      .description("Interview Api")
      .contact(new Contact("Isaac Yuen", "", "whyuenac.job@gmail.com"))
      .version("0.0.1")
      .license("No License")
      .build();
  }
}
