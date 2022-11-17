package com.example.producer.mq.send;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SendMessage {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void send(String topic, String key, Object payload) {

    ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key ,payload);
    SuccessCallback<SendResult<String, Object>> successCallback = sendResult -> {
      log.info("Sent payload='{}' with key='{}' to topic-partition@offset='{}'", payload, key, sendResult.getRecordMetadata().toString());
    };
    FailureCallback failureCallback = throwable -> {
      log.info("Sending payload='{}' to topic='{}' with key='{}' failed!!!", payload, topic, key);
    };
    future.addCallback(successCallback, failureCallback);

  }

}
