package com.crowninteractive.assessment.transaction.controller;

import com.crowninteractive.assessment.common.dto.PaginatedResponse;
import com.crowninteractive.assessment.common.response.ApiResponse;
import com.crowninteractive.assessment.transaction.dto.request.CreateTransactionRequest;
import com.crowninteractive.assessment.transaction.dto.response.TransactionResponseDto;
import com.crowninteractive.assessment.transaction.service.ITransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("${api.prefix}/transactions")
@RequiredArgsConstructor
public class TransactionController {
  private final ITransactionService transactionService;

  @PostMapping
  public ResponseEntity<ApiResponse> createTransaction(
      @Valid @RequestBody CreateTransactionRequest request
  ) {

    TransactionResponseDto transaction =
        transactionService.createTransaction(request);

    return ResponseEntity
        .status(CREATED)
        .body(
            new ApiResponse(
                "success",
                "Transaction created",
                transaction
            )
        );
  }


  @GetMapping("/{slug}")
  public ResponseEntity<ApiResponse> getTransactionBySlug(
      @PathVariable String slug
  ) {
      TransactionResponseDto transaction =
          transactionService.getTransactionBySlug(slug);

      return ResponseEntity.ok(
          new ApiResponse(
              "success",
              "Transaction retrieved",
              transaction
          )
      );

  }


  @GetMapping
  public ResponseEntity<ApiResponse> getAllTransactions(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int limit
  ) {

    Pageable pageable = PageRequest.of(page - 1, limit);
    Page<TransactionResponseDto> transactionPage =
        transactionService.getAllTransactions(pageable);

    PaginatedResponse<TransactionResponseDto> response =
        PaginatedResponse.from(
            transactionPage,
            page
        );

    return ResponseEntity.ok(
        new ApiResponse(
            "success",
            "Transactions retrieved",
            response
        )
    );
  }
}
