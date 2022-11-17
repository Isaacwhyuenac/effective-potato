//package com.example.consumer.dto;
//
//import java.time.LocalDate;
//
////import javax.validation.constraints.NotBlank;
//
//import org.springframework.format.annotation.DateTimeFormat;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//import lombok.Data;
//
//@Data
//public class TransactionDto {
//  public static final String DAY_TIME_PATTERN = "MM-dd-yyyy";
//
////  @NotBlank
//  private String amount;
//
//  private String iban;
//
//  @JsonFormat(pattern = DAY_TIME_PATTERN, shape = JsonFormat.Shape.STRING)
//  @DateTimeFormat(pattern = DAY_TIME_PATTERN, iso = DateTimeFormat.ISO.DATE)
////  @JsonSerialize(using = LocalDateSerializer.class)
////  @JsonDeserialize(using= LocalDateDeserializer.class)
//  private LocalDate date;
//
//  private String description;
//}
//
