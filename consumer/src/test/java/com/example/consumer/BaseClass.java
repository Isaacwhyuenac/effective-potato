package com.example.consumer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.consumer.controller.TransactionController;
import com.example.consumer.repository.TransactionRepository;
import com.example.entity.Transaction;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
//@AutoConfigureMessageVerifier
@ExtendWith(SpringExtension.class)
public abstract class BaseClass {

  @Autowired
  private TransactionController transactionController;

  @MockBean
  private TransactionRepository transactionRepository;

  @BeforeEach
  public void setUp() {
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

    BDDMockito.given(
        this.transactionRepository.findById(id)
      )
      .willReturn(
        Optional.ofNullable(Transaction.builder().id(id).amount(amount).iban(iban).date(localDate).description(description).build())
      );

    BDDMockito.given(
        this.transactionRepository.findById(UUID.fromString("A8AEC7C1-E05C-43D7-9BEB-5C06ACF8C9EE"))
      )
      .willReturn(
        Optional.ofNullable(Transaction.builder().id(id).amount(amount).iban(iban).date(localDate).description(description).build())
      );

    RestAssuredMockMvc.standaloneSetup(this.transactionController);
  }
}
