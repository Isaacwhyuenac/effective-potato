package com.example.producer.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.entity.Transaction;
import com.example.producer.mq.send.SendMessage;
import com.example.producer.utils.PageUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionService {

  @Value("${services.consumer-url}")
  private String consumerServiceUrl;

  private RestTemplate restTemplate;


  @Autowired
  private SendMessage sendMessage;

  @Value("${kafka.topic.transaction:transaction}")
  private String transactionTopic;


  public TransactionService(
    RestTemplate restTemplate,
    SendMessage sendMessage
  ) {
    this.restTemplate = restTemplate;
    this.sendMessage = sendMessage;
  }

  /**
   * @param pageable
   * @return list of transaction records
   * @see Page
   */
  public PageUtil<Transaction> getAllTransactions(Pageable pageable) {
//    Page<Transactions> transactions = transactionRepository.findAll(pageable);
    log.info("Querying [pagenumber: " + pageable.getPageNumber() + "]");

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    ResponseEntity<PageUtil<Transaction>> response = this.restTemplate.exchange(
      consumerServiceUrl + "/transaction?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize(),
      HttpMethod.GET,
      new HttpEntity<>(headers),
      new ParameterizedTypeReference<>() {
      }
    );

    return response.getBody();
  }

  /**
   * @param id
   * @return transaction record if exist or null
   */
  public Optional<Transaction> getTransaction(UUID id) {
    log.info("Querying [transactionId: " + id + "]");


    log.info("apiPath " + consumerServiceUrl);

    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    ResponseEntity<Optional<Transaction>> response = this.restTemplate.exchange(
      consumerServiceUrl + "/transaction/" + id,
      HttpMethod.GET,
      new HttpEntity<>(headers),
      new ParameterizedTypeReference<>() {
      }
    );

    log.info("[transactionId: " + id + "] no item match");
    return response.getBody();
  }

  /**
   * @param transaction
   * @return uuid of the transaction record
   */
  public UUID postTransaction(Transaction transaction) {
    UUID uuid = UUID.randomUUID();
    transaction.setId(uuid);

    log.info("[transactionId: " + uuid + "] is now sent to the consumer to the consumer service");

    sendMessage.send(transactionTopic, transaction.getIban(), transaction);

    return uuid;
  }

}
