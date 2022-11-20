package com.example.consumer.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.consumer.service.TransactionService;
import com.example.entity.Transaction;

import lombok.RequiredArgsConstructor;

@RestController()
@RequestMapping(value = "/transaction")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;

  @GetMapping
  public ResponseEntity<Page<Transaction>> getAllTransaction(@PageableDefault(page = 0, size = 20) Pageable pageable) {
    return ResponseEntity.ok(transactionService.getAllTransactions(pageable));
  }

  @GetMapping(value = "/{uuid}")
  public ResponseEntity<Transaction> getTransaction(@PathVariable UUID uuid) {
    Optional<Transaction> transaction = transactionService.getTransaction(uuid);
    return ResponseEntity.ok(transaction.orElse(Transaction.builder().build()));
  }

  @GetMapping(value = "/test")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok().body("Hello World");
  }

}
