package com.example.synpulse8.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.synpulse8.dto.TransactionDto;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionsController {

  @GetMapping
  public ResponseEntity<String> getTransaction() {
    return ResponseEntity.ok().build();
  }

  @PostMapping
  public ResponseEntity<String> createTransaction(@Validated @RequestBody TransactionDto transactionDto) {

    return ResponseEntity.ok().build();
  }

}
