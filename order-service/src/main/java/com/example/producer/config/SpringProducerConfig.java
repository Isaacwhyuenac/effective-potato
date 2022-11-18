package com.example.producer.config;

import java.util.Optional;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringProducerConfig {

  @Bean
  public NewTopic transactionTopic(
    @Value("${kafka.topic.transaction:#{null}}"
    ) Optional<String> transactionTopic) {
    String topic = transactionTopic.isPresent() ? transactionTopic.get() : "transaction";

    return new NewTopic(topic, 1, (short) 1);
  }



}
