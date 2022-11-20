package com.example.producer.controller;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.entity.Transaction;
import com.example.producer.service.TransactionService;
import com.example.producer.utils.PageUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

  private final TransactionService transactionService;

  @Operation(
    summary = "Get All Transactions",
    tags = {"GET", "Transaction"},
    method = "GET",
    responses = {
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
    }
  )
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<PageUtil<Transaction>> getAllTransaction(
    @PageableDefault(page = 0, size = 20) Pageable pageable
  ) {
    log.info("Print Pageable size " + pageable.getPageSize());
    PageUtil<Transaction> transactionResponse = transactionService.getAllTransactions(pageable);

    return ResponseEntity.ok(transactionResponse);
  }

  @Operation(summary = "Get Transactions by id", tags = {"GET", "Transaction"})
  @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Transaction> getTransaction(@PathVariable UUID id) {
    log.info("Here we call uuid " + id);
    Optional<Transaction> transaction = transactionService.getTransaction(id);

    return transaction.isPresent() ? ResponseEntity.ok().body(transaction.get()) : ResponseEntity.noContent().build();
  }


  @Operation(summary = "Create Transactions", tags = {"POST", "Transaction"})
  @PostMapping
  public ResponseEntity<Void> createTransaction(@Valid @RequestBody Transaction transaction) {
    UUID uuid = transactionService.postTransaction(transaction);

    URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass()).getTransaction(uuid)).build(uuid);
    return ResponseEntity.created(location).build();
  }


}
