package com.example.producer.controller;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import com.example.entity.Transaction;
import com.example.producer.service.TransactionService;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.parsing.Parser;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Slf4j
public class ApplicationRestAssuredMockMvcTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private TransactionService transactionService;

  @LocalServerPort
  private int port;

  private final String apiPath = "/transaction";

  @BeforeEach
  public void setUp() {
    RestAssured.port = this.port;
    RestAssured.defaultParser = Parser.JSON;
    RestAssuredMockMvc.reset();
    RestAssuredMockMvc.config().getMockMvcConfig().automaticallyApplySpringSecurityMockMvcConfigurer();
    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
  }

//  @Test
//  @WithMockUser(username = "testUser")
//  public void testGetAllTransactions() throws Exception {
//    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
//    String amount = "CHF 1000";
//    String iban = "CH93-0000-0000-0000-0000-0";
//    LocalDate localDate = LocalDate.of(2020, 1, 22);
//    String description = "Online payment CHF";
//
//    List<Transaction> content = Arrays.asList(
//      Transaction.builder().id(id).amount(amount).iban(iban).date(localDate).description(description).build(),
//      Transaction.builder().id(UUID.randomUUID()).amount("GBP 1000").iban(iban).date(localDate).description(description).build(),
//      Transaction.builder().id(UUID.randomUUID()).amount("EURO 1000").iban(iban).date(localDate).description(description).build()
//    );
//
//    PageUtil<Transaction> transactionPageUtil = new PageUtil<>(1, 1, content, content.size());
//
//    PageRequest pageable = PageRequest.of(0, 20);
//
//    BDDMockito.given(this.transactionService
//        .getAllTransactions(pageable))
//      .willReturn(
//        transactionPageUtil
//      );
//
//    // @formatter:off
//    Response response = RestAssured
//      .when()
//        .get(apiPath)
//      .then()
////        .body("content[0].amount", Matchers.is(amount))
////        .body("content[1].amount", Matchers.is("GBP 1000"))
////        .body("content[2].amount", Matchers.is("EURO 1000"))
////      .statusCode(HttpStatus.OK.value())
//      .extract().response();
//
//    System.out.println(response.getBody());
//    // @formatter:on
//
//    Mockito.verify(this.transactionService, Mockito.times(1)).getAllTransactions(Mockito.any(PageRequest.class));
//    Mockito.verifyNoMoreInteractions(this.transactionService);
//  }

  @Test
//  @WithMockUser(username = "testUser")
  public void testGetTransaction() throws Exception {
    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    BDDMockito.given(this.transactionService.getTransaction(id)).willReturn(Optional.ofNullable(Transaction.builder().amount(amount).iban(iban).date(localDate).description(description).build()));

    // @formatter:off
    RestAssured
      .when()
        .get(apiPath + "/" + id)
      .then()
        .body("amount", Matchers.is(amount))
        .body("description", Matchers.is(description))
        .body("iban", Matchers.is(iban))

      .statusCode(HttpStatus.OK.value());
    // @formatter:on

    Mockito.verify(this.transactionService, Mockito.times(1)).getTransaction(id);
    Mockito.verifyNoMoreInteractions(this.transactionService);
  }


}
