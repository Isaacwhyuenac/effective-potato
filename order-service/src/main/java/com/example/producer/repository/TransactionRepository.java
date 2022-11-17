package com.example.producer.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.producer.domain.Transactions;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transactions, UUID> {

}
