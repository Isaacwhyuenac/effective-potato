//package com.example.producer.service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.example.producer.controller.TransactionController;
//import com.example.producer.domain.Transaction;
//import com.example.producer.dto.TransactionDto;
//
//import lombok.extern.slf4j.Slf4j;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = {TransactionController.class})
//@Slf4j
//class TransactionServiceTest {
////  public static final UUID transactionId = BaseClass.transactionId;
//
////  public static final Transactions transactionDto = new Transactions(transactionId, "CHF 1000", "CH93-0000-0000-0000-0000-0", LocalDate.of(2020, 1, 22), "Online payment CHF");
//
////  @MockBean
////  private TransactionRepository transactionRepository;
//
////  @Autowired
////  private ModelMapper modelMapper;
//
////  @Autowired
////  private SendMessage sendMessage;
//
//  @Autowired
//  private TransactionService transactionService;
//
////  @Autowired
////  private NewTopic transactionTopic;
//
//
//
//  @BeforeEach
//  public void setUp() {
//
////    transactionService = new TransactionService(transactionRepository, modelMapper, sendMessage, transactionTopic);
//  }
//
//  @AfterEach
//  public void tearDown() {
////    Mockito.reset(transactionRepository);
//  }
//
//  @Test
//  void shouldGetAllTransactions() {
//    UUID id = UUID.fromString("5EF60C78-2D38-4936-A736-235E0A6B2177");
//    String amount = "CHF 1000";
//    String iban = "CH93-0000-0000-0000-0000-0";
//    LocalDate localDate = LocalDate.of(2020, 1, 22);
//    String description = "Online payment CHF";
//
////    BDDMockito.given(
////
////    )
//
//
//    List<Transaction> transactionList = List.of(transactionDto);
//    Page<Transaction> transactionsPage = new PageImpl<>(transactionList);
//    Page<TransactionDto> expectedResult = new PageImpl<>(
//      transactionsPage.stream().map(transaction -> modelMapper.map(transaction, TransactionDto.class)).collect(Collectors.toList())
//    );
//
//    Mockito.when(transactionRepository.findAll(ArgumentMatchers.any(Pageable.class)))
//      .thenReturn(transactionsPage);
//    Page<Transaction> actualResult = transactionService.getAllTransactions(Pageable.ofSize(20));
//
//    Assertions.assertEquals(expectedResult.getContent(), actualResult.getContent());
//  }
//
//  @Test
//  void testGetAllTransactions_returnEmptyList() {
//    Page<Transaction> transactionsPage = Page.empty();
//
//    Mockito.when(transactionRepository.findAll(ArgumentMatchers.any(Pageable.class)))
//      .thenReturn(transactionsPage);
//    Page<Transaction> actualResult = transactionService.getAllTransactions(Pageable.ofSize(20));
//
//    Assertions.assertTrue(actualResult.isEmpty());
//  }
//
//  @Test
//  void testGetTransaction() {
//    Optional<Transaction> expectedResult = Optional.of(transactionDto);
//
//    Mockito.when(transactionRepository.findById(transactionId))
//      .thenReturn(expectedResult);
////    Optional<Transactions> expectedResult = Optional.of(modelMapper.map(transactionsOptional.get(), TransactionDto.class));
//
//    Optional<Transaction> actualResult = transactionService.getTransaction(transactionId);
//
//    Assertions.assertEquals(expectedResult, actualResult);
//  }
//
//  @Test
//  void testGetTransaction_returnEmpty() {
//    Optional<Transaction> transactionsOptional = Optional.empty();
//
//    Mockito.when(transactionRepository.findById(transactionId))
//      .thenReturn(transactionsOptional);
//
//    Optional<Transaction> actualResult = transactionService.getTransaction(transactionId);
//
//    Assertions.assertTrue(actualResult.isEmpty());
//  }
//}