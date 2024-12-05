package com.job.micro.accounttx.service;

import com.job.micro.accounttx.dto.TransactionDTO;

import java.time.Instant;
import java.util.List;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    List<TransactionDTO> getAllTransactions();

    TransactionDTO getTxById(Long transactionId);

    List<TransactionDTO> getTxByAccountId(Long accountId);

    List<TransactionDTO> getTxByAccountIdAndDateBetween(Long accountId, Instant dateStart, Instant dateEnd);

    TransactionDTO updateTransaction(Long transactionId, TransactionDTO transactionDTO);

    void deleteTransaction(Long transactionId);

}
