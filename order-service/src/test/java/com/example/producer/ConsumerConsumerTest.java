package com.example.producer;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.entity.Transaction;
import com.example.producer.service.TransactionService;
import com.example.producer.utils.PageUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
  properties = {
    "services.consumer-url=http://localhost:${stubrunner.runningstubs.consumer.port}"
  }
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
  public void testGetAllTransaction() {

    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177".toLowerCase());
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    Pageable pageable = PageRequest.of(0, 20);

    PageUtil<Transaction> transactionPage = this.transactionService.getAllTransactions(pageable);

    Assertions.assertEquals(1, transactionPage.getTotalElements());
    Assertions.assertEquals(1, transactionPage.getPage());
    Assertions.assertEquals(1, transactionPage.getCurrentPage());
    Assertions.assertEquals(id, transactionPage.getContent().get(0).getId());
    Assertions.assertEquals(amount, transactionPage.getContent().get(0).getAmount());
  }

  @Test
  public void testGetAllEmptyTransaction() {

    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177".toLowerCase());
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    PageRequest pageable = PageRequest.of(0, 3);

    PageUtil<Transaction> transactionPage = this.transactionService.getAllTransactions(pageable);

    Assertions.assertEquals(0, transactionPage.getTotalElements());
    Assertions.assertEquals(1, transactionPage.getPage());
    Assertions.assertEquals(1, transactionPage.getCurrentPage());
    Assertions.assertEquals(0, transactionPage.getContent().size());

  }


  @Test
  public void testGetTransaction() {

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

    Optional<Transaction> transactionActual = this.transactionService.getTransaction(id);

    Assertions.assertEquals(transaction, transactionActual.get());
  }

}
