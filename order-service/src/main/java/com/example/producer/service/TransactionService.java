package com.example.producer.service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.producer.domain.Transactions;
import com.example.producer.dto.TransactionDto;
import com.example.producer.mq.send.SendMessage;
import com.example.producer.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

  private final TransactionRepository transactionRepository;

  private final ModelMapper modelMapper;

  private final SendMessage sendMessage;

  private final NewTopic transactionTopic;

  public Page<TransactionDto> getAllTransaction(Pageable pageable) {
    Page<Transactions> transactions = transactionRepository.findAll(pageable);

    if (!transactions.isEmpty()) {
      return new PageImpl<>(
        transactions.stream().map(transaction -> modelMapper.map(transaction, TransactionDto.class)).collect(Collectors.toList()),
        PageRequest.of(transactions.getTotalPages(), transactions.getSize(), transactions.getSort()),
        transactions.getTotalElements()
      );
    }

    return Page.empty();
  }

  public Optional<TransactionDto> getTransaction(UUID id) {
    Optional<Transactions> transactionsOptional = transactionRepository.findById(id);

    if (transactionsOptional.isPresent()) {
      return Optional.of(modelMapper.map(transactionsOptional.get(), TransactionDto.class));
    }

    return Optional.empty();
  }

  public UUID postTransaction(TransactionDto transactionDto) {

    Transactions transactions = modelMapper.map(transactionDto, Transactions.class);
    UUID uuid = UUID.randomUUID();

    transactions.setId(uuid);

    sendMessage.send(transactionTopic.name(), transactions.getIban(), transactions);

    return uuid;
  }

}
