package com.example.consumer.mq.receiver;

import java.util.stream.StreamSupport;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.example.consumer.costants.Constants;
import com.example.consumer.service.TransactionService;
import com.example.entity.Transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionHandler {

  private final TransactionService transactionService;


  @Bean
  public RecordMessageConverter jsonConverter() {
    return new StringJsonMessageConverter();
  }

  @Bean
  public RecordMessageConverter converter() {
    return new StringJsonMessageConverter();
  }

  @KafkaListener(topics = {Constants.KAFKA_TRANSACTION_TOPIC}, groupId = Constants.KAFKA_GROUP_ID, clientIdPrefix = Constants.KAFKA_CLIENT_ID)
  public void handle(
    ConsumerRecord<String, Transaction> consumerRecord,
    @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
    Message<Transaction> transactionMessage
  ) {
    Transaction transaction = transactionMessage.getPayload();
    log.info("Logger 2 [String] received key {}: Type [{}] | Payload: {} | Record: {}", key,
      typeIdHeader(consumerRecord.headers()), transaction, consumerRecord);

    transactionService.createTransaction(transaction);
  }

  private static String typeIdHeader(Headers headers) {
    return StreamSupport.stream(headers.spliterator(), false)
      .filter(header -> header.key().equals("__TypeId__"))
      .findFirst().map(header -> new String(header.value())).orElse("N/A");
  }

}
