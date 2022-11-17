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

import com.example.producer.dto.TransactionDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
//  classes = {TestConfiguration.class},
  webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
//@EnableAutoConfiguration(exclude = {
//  DataSourceAutoConfiguration.class,
//  DataSourceTransactionManagerAutoConfiguration.class,
//  HibernateJpaAutoConfiguration.class
//})
//@AutoConfigureWireMock(port = 0)
//@DirtiesContext
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

//  @Test
//  public void assertMustFail() {
//    Assertions.assertEquals(true, false);
//  }

//  @Test
//  public void assertCreateTransaction() {
//    String expectedPath = "/transactions";
//
////    WireMock.stubFor(
////      WireMock.post(WireMock.urlEqualTo(expectedPath))
//////        .withRequestBody()
////        .willReturn(
////          WireMock.aResponse()
////            .withStatus(HttpStatus.CREATED.value())
//////            .withHeader(HttpHeaders.LOCATION, )
////        )
////    );
//
////    UUID uuid = UUID.fromString("");
//
////    TransactionDto transactionDto = new TransactionDto();
////    transactionDto.setId(uuid);
////    transactionDto.setAmount("CHF 1000");
////    transactionDto.setIban("CH93-0000-0000-0000-0000-0");
////    transactionDto.setDate(LocalDate.parse("01-22-2022"));
////    transactionDto.setDescription("Online payment CHF");
//
////    RestTemplate restTemplate = new RestTemplate();
////    HttpEntity<TransactionDto> request = new HttpEntity<>(transactionDto);
//
////    ResponseEntity<TransactionDto> response = restTemplate.postForEntity("http://localhost:" + port + expectedPath, request, TransactionDto.class);
//  }
//
//  @Test
//  @WithMockUser(username = "testUser", authorities = {"SCOPE_read:messages"})
//  public void assertCallingGetTransaction_thenReturnOK() {
//    String expectedPath = "/transactions";

//    WireMock.stubFor(
//      WireMock.get(WireMock.urlEqualTo(expectedPath))
//        .willReturn(
//          WireMock.aResponse().withStatus(HttpStatus.OK.value())
//            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
//    );

//    mockMvc.perform(MockMvcRequestBuilders.post(""))
//    ResponseEntity<String> response = mockMvc.getForEntity("http://localhost:" + port + expectedPath, String.class);

//    Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
//    System.out.println(response.getBody());
//    WireMock.verify(WireMock.getRequestedFor(WireMock.urlMatching(expectedPath)));
//  }


}