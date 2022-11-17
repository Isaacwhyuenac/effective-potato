package com.example.producer.dto;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
  public static final String DAY_TIME_PATTERN = "MM-dd-yyyy";

  private UUID id;

  @NotBlank
  private String amount;

  private String iban;

  @JsonFormat(pattern = DAY_TIME_PATTERN, shape = JsonFormat.Shape.STRING)
  @DateTimeFormat(pattern = DAY_TIME_PATTERN, iso = DateTimeFormat.ISO.DATE)
  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate date;

  private String description;

}
