package com.example.consumer.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.consumer.service.TransactionService;
import com.example.entity.Transaction;

import lombok.extern.slf4j.Slf4j;


@ExtendWith(value = {SpringExtension.class})
@Slf4j
@WebMvcTest(
  controllers = {
    TransactionController.class
  }
)
class TransactionControllerTest {

  @MockBean
  private TransactionService transactionService;


  @Autowired
  MockMvc mockMvc;

  private final String apiPath = "/transaction";

  @Test
  public void getAllTransaction() throws Exception {
    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    BDDMockito.given(
        this.transactionService.getAllTransactions(Mockito.any(Pageable.class))
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

    MvcResult mvcResult = this.mockMvc
      .perform(
        MockMvcRequestBuilders.get(apiPath)
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id", CoreMatchers.hasItem(id.toString())))
      .andReturn();

    log.debug("mvc result::" + mvcResult.getResponse().getContentAsString());

    Mockito.verify(this.transactionService, Mockito.times(1)).getAllTransactions(Mockito.any(Pageable.class));
    Mockito.verifyNoMoreInteractions(this.transactionService);
  }

  @Test
  public void getTransaction() throws Exception {
    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
    String amount = "CHF 1000";
    String iban = "CH93-0000-0000-0000-0000-0";
    LocalDate localDate = LocalDate.of(2020, 1, 22);
    String description = "Online payment CHF";

    Transaction transaction = Transaction
      .builder()
      .id(id)
      .amount(amount)
      .iban(iban)
      .date(localDate)
      .description(description)
      .build();

    BDDMockito.given(
      this.transactionService.getTransaction(id)
    ).willReturn(Optional.of(transaction));

    MvcResult mvcResult = this.mockMvc
      .perform(
        MockMvcRequestBuilders.get(apiPath + "/" + id)
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(id.toString())))
      .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.equalTo(amount)))
      .andExpect(MockMvcResultMatchers.jsonPath("$.iban", Matchers.equalTo(iban)))
      .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.equalTo(description)))
      .andReturn();

    log.debug("mvc result::" + mvcResult.getResponse().getContentAsString());

    Mockito.verify(this.transactionService, Mockito.times(1)).getTransaction(Mockito.any(UUID.class));
    Mockito.verifyNoMoreInteractions(this.transactionService);
  }
}