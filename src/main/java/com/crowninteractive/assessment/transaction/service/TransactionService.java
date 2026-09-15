package com.crowninteractive.assessment.transaction.service;

import com.crowninteractive.assessment.common.enums.CurrencyCode;
import com.crowninteractive.assessment.common.enums.TransactionChannel;
import com.crowninteractive.assessment.common.enums.TransactionStatus;
import com.crowninteractive.assessment.common.exceptions.AlreadyExistsException;
import com.crowninteractive.assessment.common.exceptions.ResourceNotFoundException;
import com.crowninteractive.assessment.transaction.dto.request.CreateTransactionRequest;
import com.crowninteractive.assessment.transaction.dto.response.TransactionResponseDto;
import com.crowninteractive.assessment.transaction.entity.Transaction;
import com.crowninteractive.assessment.transaction.repository.TransactionRepository;
import com.crowninteractive.assessment.transaction.specification.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService{
  private final TransactionRepository transactionRepository;

  @Transactional
  @Override
  public TransactionResponseDto createTransaction(
      CreateTransactionRequest request
  ) {

    if (transactionRepository.existsByTransactionReference(
        request.transactionReference()
    )) {
      throw new AlreadyExistsException("Transaction reference already exists");
    }

    Transaction transaction = new Transaction();

    transaction.setTransactionReference(request.transactionReference());
    transaction.setAccountNumber(request.accountNumber());
    transaction.setTransactionType(request.transactionType());
    transaction.setChannel(request.channel());
    transaction.setAmount(request.amount());
    transaction.setCurrency(
        request.currency() != null
            ? request.currency()
            : com.crowninteractive.assessment.common.enums.CurrencyCode.NGN
    );
    transaction.setStatus(request.status());
    transaction.setTransactionDate(request.transactionDate());
    transaction.setSource(request.source());

    transaction = transactionRepository.save(transaction);

    return convertToDto(transaction);
  }


  @Transactional(readOnly = true)
  @Override
  public TransactionResponseDto getTransactionBySlug(String slug) {

    Transaction transaction = transactionRepository.findBySlug(slug)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Transaction not found"));
    return convertToDto(transaction);
  }


  @Transactional(readOnly = true)
  @Override
  public Page<TransactionResponseDto> getAllTransactions(
      String accountNumber,
      TransactionChannel channel,
      Instant fromDate,
      Instant toDate,
      CurrencyCode currency,
      TransactionStatus status,
      Pageable pageable
  ) {
    Specification<Transaction> specification = Specification.unrestricted();

    if (accountNumber != null && !accountNumber.isBlank()) {
      specification = specification.and(
          TransactionSpecification.hasAccountNumber(accountNumber)
      );
    }

    if (channel != null) {
      specification = specification.and(
          TransactionSpecification.hasChannel(channel)
      );
    }

    if (fromDate != null) {
      specification = specification.and(
          TransactionSpecification.transactionDateFrom(fromDate)
      );
    }

    if (toDate != null) {
      specification = specification.and(
          TransactionSpecification.transactionDateTo(toDate)
      );
    }

    if (currency != null) {
      specification = specification.and(
          TransactionSpecification.hasCurrency(currency)
      );
    }

    if (status != null) {
      specification = specification.and(
          TransactionSpecification.hasStatus(status)
      );
    }

    return transactionRepository.findAll(specification, pageable)
        .map(this::convertToDto);
  }

  private TransactionResponseDto convertToDto(Transaction transaction) {
    return new TransactionResponseDto(

        transaction.getSlug(),
        transaction.getTransactionReference(),
        transaction.getAccountNumber(),
        transaction.getTransactionType(),
        transaction.getChannel(),
        transaction.getAmount(),
        transaction.getCurrency(),
        transaction.getStatus(),
        transaction.getTransactionDate(),
        transaction.getSource()
    );
  }
}
