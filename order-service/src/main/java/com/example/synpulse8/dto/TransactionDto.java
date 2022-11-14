package com.example.synpulse8.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class TransactionDto {
  private String amount;
  private String iban;
  private LocalDate date;
  private String description;
}
