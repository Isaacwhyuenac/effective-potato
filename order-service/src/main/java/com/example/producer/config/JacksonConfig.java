package com.example.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class JacksonConfig {
//  @Bean
//  public ObjectMapper objectMapper() {
//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.registerModule(new JavaTimeModule());
////    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
////    objectMapper.findAndRegisterModules();
//    return objectMapper;
//  }

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

  @Bean
  public Jackson2ObjectMapperBuilder objectMapperBuilder() {
    return new Jackson2ObjectMapperBuilder()
      .serializationInclusion(JsonInclude.Include.NON_EMPTY)
      .featuresToDisable(
        SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,
        DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
      )
      .featuresToEnable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
      .indentOutput(true);
  }
}
