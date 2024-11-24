package com.job.micro.accounttx.service.impl;

import com.job.micro.accounttx.dto.TransactionDTO;
import com.job.micro.accounttx.entity.Account;
import com.job.micro.accounttx.entity.Transaction;
import com.job.micro.accounttx.exception.AccountIdNotFoundException;
import com.job.micro.accounttx.exception.TransactionIdNotFoundException;
import com.job.micro.accounttx.exception.UnavailableBalanceException;
import com.job.micro.accounttx.repository.AccountRepository;
import com.job.micro.accounttx.repository.TransactionRepository;
import com.job.micro.accounttx.service.TransactionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private ModelMapper modelMapper;

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);

        Account account = accountRepository
                .findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new AccountIdNotFoundException("Account id not found in db! : " + transactionDTO.getAccountId()));

        transaction.setAccount(account);
        transaction.setBalanceBeforeTx(account.getBalance());

        switch (transaction.getType()) {
            case DEPOSIT -> transaction.setBalance(account.getBalance() + transaction.getAmount());
            case WITHDRAWAL -> {
                if (transaction.getAmount() > account.getBalance()) {
                    throw new UnavailableBalanceException("Balance unavailable");
                }

                transaction.setBalance(account.getBalance() - transaction.getAmount());
            }
        }

        account.setBalance(transaction.getBalance());
        accountRepository.save(account);

        Transaction savedTx = transactionRepository.save(transaction);
        return modelMapper.map(savedTx, TransactionDTO.class);
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return Arrays.asList(
                modelMapper.map(transactionRepository.findAll(), TransactionDTO[].class)
        );
    }

    @Override
    public TransactionDTO getTxById(Long transactionId) {
        Transaction transaction = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionIdNotFoundException("Transaction id not found in db! : " + transactionId));
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    @Override
    public List<TransactionDTO> getTxByAccountId(Long accountId) {
        return Arrays.asList(
                modelMapper.map(transactionRepository.findAllByAccountId(accountId), TransactionDTO[].class)
        );
    }

    @Override
    public List<TransactionDTO> getTxByAccountIdAndDateBetween(Long accountId, Instant dateStart, Instant dateEnd) {
        return Arrays.asList(
                modelMapper.map(
                        transactionRepository.findAllByAccountIdAndDateBetween(accountId, dateStart, dateEnd),
                        TransactionDTO[].class)
        );
    }

    @Override
    public TransactionDTO updateTransaction(Long transactionId, TransactionDTO transactionDTO) {
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);

        Transaction existingTx = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionIdNotFoundException("Transaction id not found in db! : " + transactionId));

        existingTx.setDate(transaction.getDate());
        existingTx.setType(transaction.getType());
        existingTx.setAmount(transaction.getAmount());
        existingTx.setBalance(transaction.getBalance());

        Transaction savedTx = transactionRepository.save(existingTx);

        return modelMapper.map(savedTx, TransactionDTO.class);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionIdNotFoundException("Transaction id not found in db! : " + transactionId));
        transactionRepository.deleteById(transaction.getId());
    }

}
