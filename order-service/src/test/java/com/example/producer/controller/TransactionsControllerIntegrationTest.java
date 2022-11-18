package com.example.producer.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.producer.BaseClass;
import com.example.producer.dto.TransactionDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
class TransactionsControllerIntegrationTest extends BaseClass {

//  @StubRunnerPort("order-service")
//  private int port;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @WithMockUser(username = "testUser", authorities = {"SCOPE_read:messages"})
  public void shouldCreateTransaction() throws Exception {
    this.mockMvc.perform(
        MockMvcRequestBuilders.post("/transactions")
          .content(asJsonString(new TransactionDto(null, "CHF 1000", "CH93-0000-0000-0000-0000-0", LocalDate.of(2020, 1, 22), "Online payment CHF")))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.LOCATION));

  }

  @Test
  @WithMockUser(username = "testUser", authorities = {"SCOPE_read:messages"})
  public void shouldReturnAllTransaction() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/transactions"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$..content[0].id").value(transactionId.toString()))
      .andExpect(MockMvcResultMatchers.jsonPath("$..content[0].amount").value("CHF 1000"))
      .andExpect(MockMvcResultMatchers.jsonPath("$..content[0].iban").value("CH93-0000-0000-0000-0000-0"))
      .andExpect(MockMvcResultMatchers.jsonPath("$..content[0].date").value("01-22-2020"))
      .andExpect(MockMvcResultMatchers.jsonPath("$..content[0].description").value("Online payment CHF"))
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @WithMockUser(username = "testUser", authorities = {"SCOPE_read:messages"})
  public void shouldReturnTransactionById() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/transactions/" + transactionId))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(transactionId.toString()))
      .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value("CHF 1000"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.iban").value("CH93-0000-0000-0000-0000-0"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.date").value("01-22-2020"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Online payment CHF"))
      .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY).writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }



}