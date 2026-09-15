package com.crowninteractive.assessment.transaction.service;

import com.crowninteractive.assessment.transaction.dto.request.CreateTransactionRequest;
import com.crowninteractive.assessment.transaction.dto.response.TransactionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITransactionService {

  TransactionResponseDto createTransaction(CreateTransactionRequest request);

  TransactionResponseDto getTransactionBySlug(String slug);

  Page<TransactionResponseDto> getAllTransactions(Pageable pageable);

}
