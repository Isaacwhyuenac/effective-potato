package com.example.consumer.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.consumer.repository.TransactionRepository;
import com.example.entity.Transaction;

import lombok.extern.slf4j.Slf4j;


@ExtendWith(SpringExtension.class)
@Slf4j
class TransactionServiceTest {

  @MockBean
  private TransactionRepository transactionRepository;

  @Autowired
  private TransactionService transactionService;

  @Test
  public void shouldGetAllTransaction() {
    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    BDDMockito.given(
        this.transactionRepository.findAll(Mockito.any(Pageable.class))
      )
      .willReturn(
        new PageImpl<>(
          Arrays.asList(
            Transaction.builder().id(id).amount(amount).iban(iban).date(localDate).description(description).build(),
            Transaction.builder().id(UUID.randomUUID()).amount(amount).iban(iban).date(localDate).description(description).build(),
            Transaction.builder().id(UUID.randomUUID()).amount(amount).iban(iban).date(localDate).description(description).build()
          )
        )
      );

    Page<Transaction> transactions = transactionService.getAllTransactions(Pageable.ofSize(20));

    Assertions.assertTrue(transactions.getContent().contains(Transaction.builder().id(id).amount(amount).iban(iban).date(localDate).description(description).build()));
  }

  @Test
  public void shouldGetTransaction() {
    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    BDDMockito.given(
        this.transactionRepository.findById(id)
      )
      .willReturn(
        Optional.ofNullable(Transaction.builder().id(id).amount(amount).iban(iban).date(localDate).description(description).build())
      );

    Optional<Transaction> transaction = transactionService.getTransaction(id);

    Assertions.assertEquals(
      Transaction.builder().id(id).amount(amount).iban(iban).date(localDate).description(description).build(),
      transaction.get()
    );
  }


  @TestConfiguration
  @Import(value = {TransactionService.class})
  static class TestConfig {

  }

}