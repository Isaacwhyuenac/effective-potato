package com.example.producer.mq.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import com.example.entity.Transaction;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SendMessage {

  @Autowired
  private KafkaTemplate<String, Transaction> kafkaTemplate;

  /**
   * @param topic   Kafka Topic
   * @param key     The key which determines which kafka partition to put message
   * @param payload The payload
   */
  public void send(String topic, String key, Transaction payload) {

    ListenableFuture<SendResult<String, Transaction>> future = kafkaTemplate.send(topic, key, payload);
    SuccessCallback<SendResult<String, Transaction>> successCallback = sendResult -> {
      log.info("Sent payload='{}' with key='{}' to topic-partition@offset='{}'", payload, key, sendResult.getRecordMetadata().toString());
    };
    FailureCallback failureCallback = throwable -> {
      log.info("Sending payload='{}' to topic='{}' with key='{}' failed!!!", payload, topic, key);
    };
    future.addCallback(successCallback, failureCallback);

  }

}
