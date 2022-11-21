package com.example.producer.config;

import java.io.IOException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RestTemplateConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    ClientHttpRequestInterceptor interceptor = new ClientHttpRequestInterceptor() {
      @Override
      public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info(String.format("request to URI %s with HTTP verb %s", request.getURI(), request.getMethodValue()));
        return execution.execute(request, body);
      }
    };
    
    return new RestTemplateBuilder().additionalInterceptors(interceptor).build();
  }
}
