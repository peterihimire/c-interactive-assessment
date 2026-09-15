package com.crowninteractive.assessment.transaction.specification;

import com.crowninteractive.assessment.common.enums.CurrencyCode;
import com.crowninteractive.assessment.common.enums.TransactionChannel;
import com.crowninteractive.assessment.common.enums.TransactionStatus;
import com.crowninteractive.assessment.transaction.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class TransactionSpecification {

  public static Specification<Transaction> hasAccountNumber(
      String accountNumber
  ) {
    return (root, query, cb) ->
        cb.equal(root.get("accountNumber"), accountNumber);
  }

  public static Specification<Transaction> hasChannel(
      TransactionChannel channel
  ) {
    return (root, query, cb) ->
        cb.equal(root.get("channel"), channel);
  }

  public static Specification<Transaction> hasCurrency(
      CurrencyCode currency
  ) {
    return (root, query, cb) ->
        cb.equal(root.get("currency"), currency);
  }

  public static Specification<Transaction> hasStatus(
      TransactionStatus status
  ) {
    return (root, query, cb) ->
        cb.equal(root.get("status"), status);
  }

  public static Specification<Transaction> transactionDateFrom(
      Instant fromDate
  ) {
    return (root, query, cb) ->
        cb.greaterThanOrEqualTo(
            root.get("transactionDate"),
            fromDate
        );
  }

  public static Specification<Transaction> transactionDateTo(
      Instant toDate
  ) {
    return (root, query, cb) ->
        cb.lessThanOrEqualTo(
            root.get("transactionDate"),
            toDate
        );
  }
}
