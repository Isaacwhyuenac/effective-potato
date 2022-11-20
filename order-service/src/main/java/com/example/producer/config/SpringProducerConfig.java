package com.example.producer.config;

import java.util.Optional;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringProducerConfig {

//  @Value("${spring.kafka.bootstrap-servers}")
//  private String[] kafkaUrl;
//
//  @Bean
//  public ProducerFactory<?, ?> producerFactory() {
//    Map<String, Object> configProps = new HashMap<>();
//    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
//    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//    return new DefaultKafkaProducerFactory<>(configProps);
//  }
//
//  @Bean
//  public KafkaTemplate<?, ?> kafkaTemplate() {
//    return new KafkaTemplate<>(producerFactory());
//  }

  @Bean
  public NewTopic transactionTopic(
    @Value("${kafka.topic.transaction:transaction}"
    ) Optional<String> transactionTopic) {
    String topic = transactionTopic.isPresent() ? transactionTopic.get() : "transaction";

    return new NewTopic(topic, 1, (short) 1);
  }



}
