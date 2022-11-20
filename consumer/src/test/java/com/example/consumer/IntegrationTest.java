//package com.example.consumer;
//
//import java.time.LocalDate;
//import java.util.UUID;
//
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.example.consumer.domain.Transaction;
//import com.example.consumer.repository.TransactionRepository;
//
//import io.restassured.RestAssured;
//import lombok.extern.slf4j.Slf4j;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(SpringExtension.class)
//@Slf4j
//public class IntegrationTest {
//  @LocalServerPort
//  private int port;
//
//  @Autowired
//  private TransactionRepository transactionRepository;
//
//  public static final UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
//  public static final String amount = "CHF 1000";
//  public static final String iban = "CH93-0000-0000-0000-0000-0";
//  public static final LocalDate localDate = LocalDate.of(2020, 1, 22);
//  public static final String description = "Online payment CHF";
//
//  @BeforeEach
//  public void setUp() {
//    RestAssured.reset();
//    RestAssured.port = this.port;
//    this.transactionRepository.deleteAll();
//
//    Transaction transaction = transactionRepository.save(
//      Transaction.builder().id(id).amount(amount).iban(iban).date(localDate).description(description).build()
//    );
//
//    log.debug("saved transaction:", transaction);
//  }
//
//  @Test
//  public void testGetNoneExistingTransaction_shouldReturn404() throws Exception {
//    // @formatter:off
//    RestAssured
//      .when()
//        .get("/transaction/" + UUID.randomUUID())
//      .then()
//        .statusCode(HttpStatus.NOT_FOUND.value());
//    // @formatter:on
//  }
//
//  @Test
//  public void testAllTransaction_shouldBeOk() {
//    // @formatter:off
//    RestAssured
//      .when()
//        .get("/transaction")
//      .then()
//        .body("content[0].description", Matchers.is(description))
//        .statusCode(HttpStatus.OK.value());
//    // @formatter:on
//  }
//
//
//  @Test
//  public void testGetTransaction_shouldBeOk() {
//    // @formatter:off
//    RestAssured
//      .when()
//        .get("/transaction/" + id)
//      .then()
//        .body("description", Matchers.is(description))
//        .statusCode(HttpStatus.OK.value());
//    // @formatter:on
//  }
//
//
//}
