package com.example.producer.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.producer.Application;
import com.example.producer.domain.Transactions;
import com.example.producer.dto.TransactionDto;
import com.example.producer.mq.send.SendMessage;
import com.example.producer.repository.TransactionRepository;
import com.example.producer.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(
  classes = {Application.class},
  webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@AutoConfigureMessageVerifier
@EmbeddedKafka(partitions = 1, topics = {"transactions"})
@ExtendWith(SpringExtension.class)
//@Import(value = {JacksonConfig.class})
@ActiveProfiles("test")
public class BaseClass {

  public static final UUID transactionId = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");

  public static final TransactionDto transactionDto = new TransactionDto();

  static {
    transactionDto.setId(transactionId);
    transactionDto.setAmount("CHF 1000");
    transactionDto.setIban("CH93-0000-0000-0000-0000-0");
    transactionDto.setDate(LocalDate.of(2020, 1, 22));
    transactionDto.setDescription("Online payment CHF");
  }

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ModelMapper modelMapper;

//  @Autowired
//  private WebApplicationContext context;

  @Autowired
  private TransactionsController transactionsController;

  @MockBean
  private TransactionRepository transactionRepository;

  @MockBean
  private SendMessage sendMessage;

  @Autowired
  private TransactionService transactionService;

//  @MockBean
//  private TransactionService transactionService;

  @BeforeEach
  public void setup() {
    Transactions transactions = modelMapper.map(transactionDto, Transactions.class);

    List<Transactions> expected = Arrays.asList(transactions);
    Page transactionsPage = new PageImpl<>(expected);

    Mockito.doNothing().when(sendMessage).send(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(Object.class));
    Mockito.when(transactionRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(transactionsPage);
    Mockito.when(transactionRepository.findById(transactionId)).thenReturn(Optional.ofNullable(transactions));
    Mockito.when(transactionRepository.save(transactions)).thenReturn(transactions);

//    Mockito.when(transactionService.postTransaction(transactionDto)).thenReturn(transactionId);

    RestAssuredMockMvc.standaloneSetup(transactionsController);
  }

  public void sendMessage() {
    transactionService.postTransaction(transactionDto);
//    sendMessage.send("transactions", transactionDto.getIban(), transactionDto);
  }



}
