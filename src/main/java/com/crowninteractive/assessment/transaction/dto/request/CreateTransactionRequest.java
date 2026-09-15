package com.crowninteractive.assessment.transaction.dto.request;

import com.crowninteractive.assessment.common.enums.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateTransactionRequest(
    @NotBlank
    String transactionReference,

    @NotBlank
    String accountNumber,

    @NotNull
    TransactionType transactionType,

    @NotNull
    TransactionChannel channel,

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 17, fraction = 2)
    BigDecimal amount,

    CurrencyCode currency,

    @NotNull
    TransactionStatus status,

    @NotNull
    Instant transactionDate,

    @NotNull
    TransactionSource source
) { }

