//package com.example.producer.config;
//
//import org.modelmapper.ModelMapper;
//import org.modelmapper.jackson.JsonNodeValueReader;
//import org.modelmapper.spring.SpringIntegration;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ModelMapperConfig implements ApplicationContextAware {
//  ApplicationContext applicationContext;
//
//  @Override
//  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//    this.applicationContext = applicationContext;
//    SpringIntegration.fromSpring(applicationContext);
//  }
//
//  @Bean
//  public ModelMapper modelMapper() {
//    ModelMapper modelMapper = new ModelMapper();
//
//    modelMapper.getConfiguration().addValueReader(new JsonNodeValueReader());
//    return modelMapper;
//  }
//}
