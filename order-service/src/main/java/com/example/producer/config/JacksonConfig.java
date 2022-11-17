package com.example.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
//    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
//    objectMapper.findAndRegisterModules();
    return objectMapper;
  }

//  @Bean
//  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//    return builder -> {
//
//      // formatter
//      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//      DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//      // deserializers
//      builder.deserializers(new LocalDateDeserializer(dateFormatter));
//      builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));
//
//      // serializers
//      builder.serializers(new LocalDateSerializer(dateFormatter));
//      builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
//    };
//  }
}
