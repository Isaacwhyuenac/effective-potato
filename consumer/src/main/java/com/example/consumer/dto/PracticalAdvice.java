package com.example.consumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PracticalAdvice {

  @JsonProperty("message")
  String message;
  @JsonProperty("identifier")
  int identifier;
}
