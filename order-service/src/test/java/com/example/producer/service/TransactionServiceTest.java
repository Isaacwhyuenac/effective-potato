package com.example.producer.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.producer.BaseClass;
import com.example.producer.domain.Transactions;
import com.example.producer.dto.TransactionDto;
import com.example.producer.mq.send.SendMessage;
import com.example.producer.repository.TransactionRepository;

@SpringBootTest
class TransactionServiceTest {
  public static final UUID transactionId = BaseClass.transactionId;

  public static final Transactions transactionDto = new Transactions(transactionId, "CHF 1000", "CH93-0000-0000-0000-0000-0", LocalDate.of(2020, 1, 22), "Online payment CHF");

  private TransactionService transactionService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private SendMessage sendMessage;

  @MockBean
  private TransactionRepository transactionRepository;

  @Autowired
  private NewTopic transactionTopic;

  @BeforeEach
  public void setup() {
    transactionService = new TransactionService(transactionRepository, modelMapper, sendMessage, transactionTopic);
  }

  @AfterEach
  public void afterEach() {
    Mockito.reset(transactionRepository);
  }

  @Test
  void testGetAllTransactions() {
    List<Transactions> transactionsList = List.of(transactionDto);
    Page<Transactions> transactionsPage = new PageImpl<>(transactionsList);
    Page<TransactionDto> expectedResult = new PageImpl<>(
      transactionsPage.stream().map(transaction -> modelMapper.map(transaction, TransactionDto.class)).collect(Collectors.toList())
    );

    Mockito.when(transactionRepository.findAll(ArgumentMatchers.any(Pageable.class)))
      .thenReturn(transactionsPage);
    Page<TransactionDto> actualResult = transactionService.getAllTransaction(Pageable.ofSize(20));

    Assertions.assertEquals(expectedResult.getContent(), actualResult.getContent());
  }

  @Test
  void testGetAllTransactions_returnEmptyList() {
    Page<Transactions> transactionsPage = Page.empty();

    Mockito.when(transactionRepository.findAll(ArgumentMatchers.any(Pageable.class)))
      .thenReturn(transactionsPage);
    Page<TransactionDto> actualResult = transactionService.getAllTransaction(Pageable.ofSize(20));

    Assertions.assertTrue(actualResult.isEmpty());
  }

  @Test
  void testGetTransaction() {
    Optional<Transactions> transactionsOptional = Optional.of(transactionDto);

    Mockito.when(transactionRepository.findById(transactionId))
      .thenReturn(transactionsOptional);
    Optional<TransactionDto> expectedResult = Optional.of(modelMapper.map(transactionsOptional.get(), TransactionDto.class));

    Optional<TransactionDto> actualResult = transactionService.getTransaction(transactionId);

    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  void testGetTransaction_returnEmpty() {
    Optional<Transactions> transactionsOptional = Optional.empty();

    Mockito.when(transactionRepository.findById(transactionId))
      .thenReturn(transactionsOptional);

    Optional<TransactionDto> actualResult = transactionService.getTransaction(transactionId);

    Assertions.assertTrue(actualResult.isEmpty());
  }
}