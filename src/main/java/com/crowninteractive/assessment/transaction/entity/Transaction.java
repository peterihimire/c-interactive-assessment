package com.crowninteractive.assessment.transaction.entity;

import com.crowninteractive.assessment.common.entity.BaseEntity;
import com.crowninteractive.assessment.common.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name="transactions",
    indexes = {
        @Index(
            name = "idx_transactions_transaction_date",
            columnList = "transaction_date"
        ),
        @Index(
            name = "idx_transactions_account_date",
            columnList = "account_number, transaction_date"
        ),
        @Index(
            name = "idx_transactions_channel",
            columnList = "channel"
        ),
        @Index(
            name = "idx_transactions_currency",
            columnList = "currency"
        ),
        @Index(
            name = "idx_transactions_status",
            columnList = "status"
        )
    })
public class Transaction extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String transactionReference;

  @Column(nullable = false)
  private String accountNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionType transactionType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionChannel channel;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CurrencyCode currency  = CurrencyCode.NGN;


  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionStatus status;

  @Column(nullable = false)
  private Instant transactionDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionSource source;
}