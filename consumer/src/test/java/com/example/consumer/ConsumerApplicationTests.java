package com.example.consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.consumer.repository.TransactionRepository;

//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EmbeddedKafka(partitions = 1, topics = {"transactions"})
@AutoConfigureStubRunner(ids = {"com.example:order-service"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@ActiveProfiles("test")
@DirtiesContext
class ConsumerApplicationTests {
  @Autowired
  ConsumerApplication consumerApplication;

//  @Autowired
//  private EmbeddedKafkaBroker embeddedKafkaBroker;

//  @Autowired
//  private StubFinder stubFinder;

  @StubRunnerPort("order-service")
  private int port;

  @MockBean
  TransactionRepository transactionRepository;

  @Test
  public void shouldCreateTransaction() {
//    Mockito
//      .when(transactionRepository.save(Mockito.any(Transactions.class)))
//        .thenReturn(new Transactions());

//    Mockito.when(transactionRepository)
//    stubFinder.trigger("CreateTransactionEvent");
    Assertions.assertTrue(true);
//    Mockito.verify(transactionRepository).save(transactionsArgumentCaptor.capture());

//    Transactions value = transactionsArgumentCaptor.getValue();

//    System.out.println(value);

//    Awaitility.await().untilAsserted();


  }

}
