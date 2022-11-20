package com.example.consumer.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.consumer.domain.Transaction;
import com.example.consumer.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;


@ExtendWith(SpringExtension.class)
@DataJpaTest()
@Slf4j
class TransactionRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private TransactionRepository transactionRepository;

  @BeforeEach
  public void setUpClass() {
    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    transactionRepository.deleteAll();

    entityManager.persist(Transaction.builder().id(id).amount(amount).iban(iban).date(localDate).description(description).build());
  }

  @Test
  public void testGetAllTransaction() {
    Page<Transaction> all = transactionRepository.findAll(Pageable.ofSize(20));
    Assertions.assertTrue(all.getTotalElements() == 1);

    Transaction transaction = all.getContent().get(0);

    Assertions.assertEquals("Online payment CHF", transaction.getDescription());
  }


}