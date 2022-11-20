package com.example.consumer.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Transaction;


@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, UUID> {
}
