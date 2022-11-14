package com.example.synpulse8.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.github.tomakehurst.wiremock.client.WireMock;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
//  classes = {TestConfiguration.class},
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
//@EnableAutoConfiguration(exclude = {
//  DataSourceAutoConfiguration.class,
//  DataSourceTransactionManagerAutoConfiguration.class,
//  HibernateJpaAutoConfiguration.class
//})
@AutoConfigureWireMock(port = 0)
class TransactionsControllerIntegrationTest {

  @LocalServerPort
  private int port;

//  @Autowired
//  private RestTemplate restTemplate;
//
//  public TransactionsControllerIntegrationTest(RestTemplate restTemplate) {
//    this.restTemplate = restTemplate;
//  }

//  @Test
//  public void assertMustFail() {
//    Assertions.assertEquals(true, false);
//  }

  @Test
  public void assertCallingGetTransaction_thenReturnOK() {
    String expectedPath = "/transactions";

    WireMock.stubFor(
      WireMock.get(WireMock.urlEqualTo(expectedPath))
        .willReturn(
          WireMock.aResponse().withStatus(HttpStatus.OK.value())
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
    );

    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + expectedPath, String.class);

    Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

//    WireMock.verify(WireMock.getRequestedFor(WireMock.urlMatching(expectedPath)));
  }


}