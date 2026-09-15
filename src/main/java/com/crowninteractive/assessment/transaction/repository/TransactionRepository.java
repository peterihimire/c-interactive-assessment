package com.crowninteractive.assessment.transaction.repository;

import com.crowninteractive.assessment.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  boolean existsByTransactionReference(String transactionReference);

  Optional<Transaction> findByTransactionReference(String transactionReference);

  Optional<Transaction> findBySlug(String slug);

}
