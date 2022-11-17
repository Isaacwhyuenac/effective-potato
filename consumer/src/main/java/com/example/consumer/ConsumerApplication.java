package com.example.consumer;

import java.util.stream.StreamSupport;

import javax.sql.DataSource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.backoff.FixedBackOff;

import com.example.consumer.domain.Transactions;
import com.example.consumer.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class ConsumerApplication {

  private final TransactionRepository transactionRepository;

  private final ObjectMapper objectMapper;

  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
    ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
    ConsumerFactory<Object, Object> kafkaConsumerFactory,
    KafkaTemplate<Object, Object> template) {
    ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    configurer.configure(factory, kafkaConsumerFactory);
    factory.setErrorHandler(new SeekToCurrentErrorHandler(
      new DeadLetterPublishingRecoverer(template), new FixedBackOff(5000L, 3))); // dead-letter after 3 tries
    return factory;
  }

  @Bean
  public RecordMessageConverter converter() {
    return new StringJsonMessageConverter();
  }

  @Bean
  public DataSourceTransactionManager dstm(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @KafkaListener(topics = {"transaction"}, clientIdPrefix = "json", groupId = "demo")
//  @Transactional("dstm")
  public void listenTransactionTopic(
    ConsumerRecord<String, String> consumerRecord,
    @Payload String payload
  ) {

    log.info("Logger 2 [String] received key {}: Type [{}] | Payload: {} | Record: {}", consumerRecord.key(),
      typeIdHeader(consumerRecord.headers()), payload, consumerRecord.toString());

    try {
      Transactions transactions = objectMapper.readValue(consumerRecord.value(), Transactions.class);

      transactionRepository.save(transactions);

//      ack.acknowledge();

    } catch (JsonProcessingException e) {
      e.printStackTrace();

      log.info("error processing transactions = " + payload);
    }

  }

  private static String typeIdHeader(Headers headers) {
    return StreamSupport.stream(headers.spliterator(), false)
      .filter(header -> header.key().equals("__TypeId__"))
      .findFirst().map(header -> new String(header.value())).orElse("N/A");
  }

}
