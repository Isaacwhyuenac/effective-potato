package com.example.producer.controller;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.producer.dto.TransactionDto;
import com.example.producer.service.TransactionService;
import com.example.producer.utils.PaginationUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionsController {

  private final TransactionService transactionService;

  @Operation(
    summary = "Get All Transactions",
    tags = {"GET", "Transaction"},
    method = "GET",
    responses = {
      @ApiResponse(responseCode = "500", description = "Internal Server Error")
    }
  )
  @GetMapping
  public ResponseEntity<Page<TransactionDto>> getAllTransaction(
    @PageableDefault(page = 0, size = 20) Pageable pageable
  ) {
    Page<TransactionDto> transaction = transactionService.getAllTransaction(pageable);

    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), transaction);

    return new ResponseEntity<>(transaction, headers, HttpStatus.OK);
  }

  @Operation(summary = "Get Transactions by id", tags = {"GET", "Transaction"})
  @GetMapping("/{id}")
  public ResponseEntity<TransactionDto> getTransaction(@PathVariable UUID id) {

    Optional<TransactionDto> transaction = transactionService.getTransaction(id);

    if (transaction.isPresent()) {
      return ResponseEntity.ok().body(transaction.get());
    }
    return ResponseEntity.noContent().build();
  }


  @Operation(summary = "Create Transactions", tags = {"POST", "Transaction"})
  @PostMapping
  public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
    log.info(transactionDto.toString());

    UUID uuid = transactionService.postTransaction(transactionDto);

    URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass()).getTransaction(uuid)).build(uuid);
    return ResponseEntity.created(location).build();
  }

  @PostMapping(value = "/test")
  public ResponseEntity<String> test(@RequestBody String string) {

    return ResponseEntity.ok().body(string);
  }

}
