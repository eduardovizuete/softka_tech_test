package com.job.micro.accounttx.controller;

import com.job.micro.accounttx.dto.TransactionDTO;
import com.job.micro.accounttx.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name = "Transaction API service - TransactionController",
        description = "TransactionController exposes REST APIs for transaction service"
)

@RestController
@AllArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private TransactionService txService;

    @Operation(
            summary = "Save transaction REST API",
            description = "Save transaction REST API is used to save a transaction object in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 Created"
    )
    // build create transaction REST API
    // http://localhost:8080/api/transaction/
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO, UriComponentsBuilder uriComponentsBuilder) {
        TransactionDTO savedTransaction = txService.createTransaction(transactionDTO);

        URI locationURI = uriComponentsBuilder
                .path("/api/transactions/" + savedTransaction.getId())
                .buildAndExpand(uriComponentsBuilder.toUriString())
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(locationURI)
                .body(savedTransaction);
    }

    @Operation(
            summary = "Get all transactions REST API",
            description = "Get all transactions REST API is used to get all transactions from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build get all transactions by id REST API
    // http://localhost:8080/api/transactions
    @GetMapping()
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = txService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Operation(
            summary = "Get transaction REST API",
            description = "Get transaction REST API is used to get a transaction from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build get transaction by id REST API
    // http://localhost:8080/api/transactions/1
    @GetMapping("{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable("id") Long transactionId) {
        TransactionDTO transaction = txService.getTxById(transactionId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @Operation(
            summary = "Get transactions by account id REST API",
            description = "Get transactions by account id REST API is used to get transactions from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build get transaction by account id REST API
    // http://localhost:8080/api/transactions/searchByAccountId
    @GetMapping("/searchByAccountId/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountId(@PathVariable("accountId") Long accountId) {
        List<TransactionDTO> transactions = txService.getTxByAccountId(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Operation(
            summary = "Get transactions by account id and range of dates REST API",
            description = "Get by account id and range of dates REST API is used to get transactions from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build get transactions by account id and range of dates
    // http://localhost:8080/api/transactions/search?
    //                                  accountId=1&
    //                                  startDate=2024-11-16T09:41:00Z&
    //                                  endDate=2024-11-17T09:42:00Z
    @GetMapping("/search")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccountIdAndDateBetween(@RequestParam Long accountId,
                                                                                      @RequestParam LocalDateTime startDate,
                                                                                      @RequestParam LocalDateTime endDate) {
        List<TransactionDTO> transactions = txService.getTxByAccountIdAndDateBetween(accountId, startDate, endDate);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Operation(
            summary = "Update transaction REST API",
            description = "Update transaction REST API is used to update a transaction from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build update transaction REST API
    // http://localhost:8080/api/transactions/1
    @PutMapping("{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable("id") Long transactionId,
                                                         @RequestBody TransactionDTO transactionDTO) {
        TransactionDTO updatedTransaction = txService.updateTransaction(transactionId, transactionDTO);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete transaction REST API",
            description = "Delete transaction REST API is used to delete a transaction from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build delete transaction REST API
    // http://localhost:8080/api/transactions/1
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable("id") Long transactionId) {
        txService.deleteTransaction(transactionId);
        return new ResponseEntity<>("Transaction successfully deleted!", HttpStatus.OK);
    }

}
