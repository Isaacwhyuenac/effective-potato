package com.example.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@EntityScan(basePackages = {"com.example.entity"})
public class ConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }

//  @Bean
//  public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
//    ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
//    ConsumerFactory<Object, Object> kafkaConsumerFactory,
//    KafkaTemplate<Object, Object> template) {
//    ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//    configurer.configure(factory, kafkaConsumerFactory);
//    factory.setErrorHandler(new SeekToCurrentErrorHandler(
//      new DeadLetterPublishingRecoverer(template), new FixedBackOff(5000L, 3))); // dead-letter after 3 tries
//    return factory;
//  }

//  @Bean
//  public RecordMessageConverter converter() {
//    return new StringJsonMessageConverter();
//  }
//
////  @Bean
////  public DataSourceTransactionManager dstm(DataSource dataSource) {
////    return new DataSourceTransactionManager(dataSource);
////  }
//
//  @KafkaListener(topics = {"transaction"}, clientIdPrefix = "json", groupId = "demo")
////  @Transactional("dstm")
//  public void listenTransactionTopic(
//    ConsumerRecord<String, String> consumerRecord,
//    @Payload String payload
//  ) {
//
//    log.info("Logger 2 [String] received key {}: Type [{}] | Payload: {} | Record: {}", consumerRecord.key(),
//      typeIdHeader(consumerRecord.headers()), payload, consumerRecord.toString());
//
//    try {
//      Transaction transaction = objectMapper.readValue(consumerRecord.value(), Transaction.class);
//
//      transactionRepository.save(transaction);
//
////      ack.acknowledge();
//
//    } catch (JsonProcessingException e) {
//      e.printStackTrace();
//
//      log.info("error processing transactions = " + payload);
//    }
//
//  }
//
//  private static String typeIdHeader(Headers headers) {
//    return StreamSupport.stream(headers.spliterator(), false)
//      .filter(header -> header.key().equals("__TypeId__"))
//      .findFirst().map(header -> new String(header.value())).orElse("N/A");
//  }

}
