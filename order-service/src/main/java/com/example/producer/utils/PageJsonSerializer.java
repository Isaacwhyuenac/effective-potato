//package com.example.producer.utils;
//
//import java.io.IOException;
//
//import org.springframework.boot.jackson.JsonComponent;
//import org.springframework.data.domain.PageImpl;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//
//@JsonComponent
//public class PageJsonSerializer extends JsonSerializer<PageImpl> {
//
//  @Override
//  public void serialize(PageImpl value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//    gen.writeStartObject();
//    gen.writeNumberField("number", value.getNumber());
//    gen.writeNumberField("numberOfElements", value.getNumber());
//    gen.writeNumberField("totalElements", value.getTotalElements());
//    gen.writeNumberField("totalPages", value.getTotalPages());
//    gen.writeNumberField("size", value.getSize());
//    serializers.defaultSerializeValue(value.getContent(), gen);
//    gen.writeEndObject();
//  }
//
//}
