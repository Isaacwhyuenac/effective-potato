package com.example.consumer.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.consumer.repository.TransactionRepository;
import com.example.entity.Transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

  private final TransactionRepository transactionRepository;

  /**
   *
   * @param transaction
   */
  public void createTransaction(com.example.entity.Transaction transaction) {
    transactionRepository.save(transaction);
  }

  /**
   * @param pageable
   * @return list of transaction records
   * @see Page
   */
  public Page<Transaction> getAllTransactions(Pageable pageable) {
    return transactionRepository.findAll(pageable);
  }

  /**
   * @param uuid
   * @return transaction record if exist or null
   */
  public Optional<Transaction> getTransaction(UUID uuid) {
    if (log.isDebugEnabled()) {
      log.info("Querying [transactionId: " + uuid + "]");
    }

    return transactionRepository.findById(uuid);
  }


}
