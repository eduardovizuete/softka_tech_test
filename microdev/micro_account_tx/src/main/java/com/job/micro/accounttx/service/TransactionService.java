package com.job.micro.accounttx.service;

import com.job.micro.accounttx.dto.TransactionDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    List<TransactionDTO> getAllTransactions();

    TransactionDTO getTxById(Long transactionId);

    List<TransactionDTO> getTxByAccountId(Long accountId);

    List<TransactionDTO> getTxByAccountIdAndDateBetween(Long accountId, LocalDateTime dateStart, LocalDateTime dateEnd);

    void deleteTransaction(Long transactionId);

}
