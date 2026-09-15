package com.crowninteractive.assessment.transaction.service;

import com.crowninteractive.assessment.common.enums.CurrencyCode;
import com.crowninteractive.assessment.common.enums.TransactionChannel;
import com.crowninteractive.assessment.common.enums.TransactionStatus;
import com.crowninteractive.assessment.transaction.dto.request.CreateTransactionRequest;
import com.crowninteractive.assessment.transaction.dto.response.TransactionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

public interface ITransactionService {

  TransactionResponseDto createTransaction(CreateTransactionRequest request);

  TransactionResponseDto getTransactionBySlug(String slug);

  Page<TransactionResponseDto> getAllTransactions(
      String accountNumber,
      TransactionChannel channel,
      Instant fromDate,
      Instant toDate,
      CurrencyCode currency,
      TransactionStatus status,
      Pageable pageable
  );

}
