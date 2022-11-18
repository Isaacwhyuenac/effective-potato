package com.example.producer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.producer.controller.TransactionsController;
import com.example.producer.domain.Transactions;
import com.example.producer.dto.TransactionDto;
import com.example.producer.mq.send.SendMessage;
import com.example.producer.repository.TransactionRepository;
import com.example.producer.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@AutoConfigureMessageVerifier
@EmbeddedKafka(partitions = 1, topics = {"transaction"})
@ExtendWith(SpringExtension.class)
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
  private Environment environment;

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

    String[] activeProfiles = environment.getActiveProfiles();

    System.out.println("================================================");
    System.out.println(Arrays.toString(activeProfiles));


    Transactions transactions = modelMapper.map(transactionDto, Transactions.class);

    List<Transactions> expected = Arrays.asList(transactions);
    Page transactionsPage = new PageImpl<>(expected);

    Mockito.doNothing().when(this.sendMessage).send(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(Object.class));
    Mockito.when(this.transactionRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(transactionsPage);
    Mockito.when(this.transactionRepository.findById(transactionId)).thenReturn(Optional.ofNullable(transactions));
    Mockito.when(this.transactionRepository.save(transactions)).thenReturn(transactions);

//    Mockito.when(transactionService.postTransaction(transactionDto)).thenReturn(transactionId);

    RestAssuredMockMvc.standaloneSetup(this.transactionsController);
  }

  @AfterEach
  public void cleanup() {
    Mockito.reset(this.transactionRepository);
    Mockito.reset(this.sendMessage);
  }

  @Autowired
  private KafkaTemplate<Object, Object> kafkaTemplate;

  public void sendMessage() {
    this.transactionService.postTransaction(transactionDto);
//    sendMessage.send("transactions", transactionDto.getIban(), transactionDto);
  }


}
