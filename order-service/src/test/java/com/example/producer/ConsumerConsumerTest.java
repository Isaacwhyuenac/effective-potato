package com.example.producer;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.entity.Transaction;
import com.example.producer.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
//@WebMvcTest(controllers = {TransactionController.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureStubRunner(
  stubsMode = StubRunnerProperties.StubsMode.LOCAL,
  consumerName = "order-service",
//  stubsPerConsumer = true,
  ids = {"com.example:consumer"}
)
//@DirtiesContext
@ActiveProfiles(value = {"test"})
@Slf4j
public class ConsumerConsumerTest {
//  @StubRunnerPort("consumer")
//  int producerPort;
//
//  @Autowired
//  private StubFinder stubFinder;

  @Autowired
  private TransactionService transactionService;

  @Test
  public void testGetTransaction() {
//    System.out.println("==============");
//    System.out.println(producerPort);
//    int port = stubFinder.findStubUrl("consumer").getPort();
//    System.out.println(port);
    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177".toLowerCase());
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    Transaction transaction = Transaction
      .builder()
      .id(id)
      .amount(amount)
      .iban(iban)
      .date(localDate)
      .description(description)
      .build();


    Optional<Transaction> transactionOptional = this.transactionService.getTransaction(id);

    Assertions.assertEquals(transaction, transactionOptional.get());
  }

  @Test
  public void testGetNoneTransaction_returnEmptyObject() {
//    System.out.println("==============");
//    System.out.println(producerPort);
//    int port = stubFinder.findStubUrl("consumer").getPort();
//    System.out.println(port);
    UUID id = UUID.fromString("A8AEC7C1-E05C-43D7-9BEB-5C06ACF8C9EE".toLowerCase());

    Optional<Transaction> transactionOptional = this.transactionService.getTransaction(id);
    Assertions.assertTrue(transactionOptional.isEmpty());
  }

//


}
