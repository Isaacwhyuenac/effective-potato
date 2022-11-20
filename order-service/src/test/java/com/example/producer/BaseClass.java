//package com.example.producer;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.example.producer.controller.TransactionController;
//import com.example.producer.domain.Transaction;
//import com.example.producer.dto.TransactionDto;
//import com.example.producer.mq.send.SendMessage;
//import com.example.producer.repository.TransactionRepository;
//import com.example.producer.service.TransactionService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import io.restassured.module.mockmvc.RestAssuredMockMvc;
//
//@SpringBootTest(
//  webEnvironment = SpringBootTest.WebEnvironment.NONE
//  // properties = {
//  //   "spring.security.user.name=testuser"
//  // }
//)
//@ExtendWith(SpringExtension.class)
////@EmbeddedKafka(partitions = 1, topics = {"transaction"})
//@AutoConfigureMessageVerifier
//@ActiveProfiles("test")
//@ImportAutoConfiguration(exclude = {UserDetailsServiceAutoConfiguration.class, SecurityAutoConfiguration.class, OAuth2ResourceServerAutoConfiguration.class})
//// @AutoConfigureJson
//public class BaseClass {
//
//  public static final UUID transactionId = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
//
//  public static final TransactionDto transactionDto = new TransactionDto();
//
//  static {
//    transactionDto.setId(transactionId);
//    transactionDto.setAmount("CHF 1000");
//    transactionDto.setIban("CH93-0000-0000-0000-0000-0");
//    transactionDto.setDate(LocalDate.of(2020, 1, 22));
//    transactionDto.setDescription("Online payment CHF");
//  }
//
//  @Autowired
//  private ObjectMapper objectMapper;
//
//  @Autowired
//  private ModelMapper modelMapper;
//
////  @Autowired
////  private WebApplicationContext context;
//
//  @Autowired
//  private TransactionController transactionController;
//
//  @MockBean
//  private TransactionRepository transactionRepository;
//
//  @MockBean
//  private SendMessage sendMessage;
//
//  @Autowired
//  private TransactionService transactionService;
//
////  @MockBean
////  private TransactionService transactionService;
//
//  @BeforeEach
//  public void setup() {
//
//    Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
//
//    List<Transaction> expected = Arrays.asList(transaction);
//    Page transactionsPage = new PageImpl<>(expected);
//
//    Mockito.doNothing().when(this.sendMessage).send(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.any(Object.class));
//    Mockito.when(this.transactionRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(transactionsPage);
//    Mockito.when(this.transactionRepository.findById(transactionId)).thenReturn(Optional.ofNullable(transaction));
//    Mockito.when(this.transactionRepository.save(transaction)).thenReturn(transaction);
//
////    Mockito.when(transactionService.postTransaction(transactionDto)).thenReturn(transactionId);
//
//    RestAssuredMockMvc.standaloneSetup(this.transactionController);
//  }
//
//  @AfterEach
//  public void cleanup() {
//    Mockito.reset(this.transactionRepository);
//    Mockito.reset(this.sendMessage);
//  }
//
//
//  public void sendMessage() {
////    this.transactionService.postTransaction(transaction);
////    sendMessage.send("transactions", transactionDto.getIban(), transactionDto);
//  }
//
//
//}
