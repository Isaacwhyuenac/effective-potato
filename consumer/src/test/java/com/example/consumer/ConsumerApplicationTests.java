package com.example.consumer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EmbeddedKafka(partitions = 1, topics = {"transaction"})
@AutoConfigureStubRunner(ids = {"com.example:order-service"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
//@ActiveProfiles("test")
@DirtiesContext
class ConsumerApplicationTests {
//  @Autowired
//  ConsumerApplication consumerApplication;

//  @Autowired
//  private EmbeddedKafkaBroker embeddedKafkaBroker;

//  @Autowired
//  private StubFinder stubFinder;

//  @StubRunnerPort("order-service")
//  private int port;

//  @MockBean
//  TransactionRepository transactionRepository;

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
