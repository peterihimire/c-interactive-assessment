package com.crowninteractive.assessment.transaction.dto.response;

import com.crowninteractive.assessment.common.enums.*;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponseDto(
    String slug,
    String transactionReference,
    String accountNumber,
    TransactionType transactionType,
    TransactionChannel channel,
    BigDecimal amount,
    CurrencyCode currency,
    TransactionStatus status,
    Instant transactionDate,
    TransactionSource source
) { }
