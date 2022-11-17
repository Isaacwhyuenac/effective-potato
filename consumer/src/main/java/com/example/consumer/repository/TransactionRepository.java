package com.example.consumer.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.consumer.domain.Transactions;


@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transactions, UUID> {
}
