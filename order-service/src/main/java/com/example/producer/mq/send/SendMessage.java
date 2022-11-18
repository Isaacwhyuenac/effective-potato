package com.example.producer.mq.send;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SendMessage {

  @Autowired
  private KafkaTemplate<Object, Object> kafkaTemplate;

  public void send(String topic, String key, Object payload) {

    ListenableFuture<SendResult<Object, Object>> future = kafkaTemplate.send(topic, key ,payload);
    SuccessCallback<SendResult<Object, Object>> successCallback = sendResult -> {
      log.info("Sent payload='{}' with key='{}' to topic-partition@offset='{}'", payload, key, sendResult.getRecordMetadata().toString());
    };
    FailureCallback failureCallback = throwable -> {
      log.info("Sending payload='{}' to topic='{}' with key='{}' failed!!!", payload, topic, key);
    };
    future.addCallback(successCallback, failureCallback);

  }

}
