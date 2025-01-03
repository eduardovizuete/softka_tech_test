package com.job.micro.accounttx.service.impl;

import com.job.micro.accounttx.dto.TransactionDTO;
import com.job.micro.accounttx.entity.Account;
import com.job.micro.accounttx.entity.Transaction;
import com.job.micro.accounttx.entity.enumeration.TypeTx;
import com.job.micro.accounttx.exception.AccountIdNotFoundException;
import com.job.micro.accounttx.exception.TransactionIdNotFoundException;
import com.job.micro.accounttx.repository.AccountRepository;
import com.job.micro.accounttx.repository.TransactionRepository;
import com.job.micro.accounttx.service.TransactionService;
import com.job.micro.accounttx.service.txstrategy.TransactionStContext;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    public static final String ACCOUNT_ID_NOT_FOUND_IN_DB = "Account id not found in db! : ";
    public static final String TRANSACTION_ID_NOT_FOUND_IN_DB = "Transaction id not found in db! : ";

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    private ModelMapper modelMapper;

    private TransactionStContext transactionStContext;

    @Override
    @Transactional()
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);

        Account account = accountRepository
                .findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new AccountIdNotFoundException(ACCOUNT_ID_NOT_FOUND_IN_DB + transactionDTO.getAccountId()));

        transactionStContext.executeTx(account, transaction);

        Transaction savedTx = transactionRepository.save(transaction);
        accountRepository.save(account);
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
                .orElseThrow(() -> new TransactionIdNotFoundException(TRANSACTION_ID_NOT_FOUND_IN_DB + transactionId));
        return modelMapper.map(transaction, TransactionDTO.class);
    }

    @Override
    public List<TransactionDTO> getTxByAccountId(Long accountId) {
        return Arrays.asList(
                modelMapper.map(transactionRepository.findAllByAccountId(accountId), TransactionDTO[].class)
        );
    }

    @Override
    public List<TransactionDTO> getTxByAccountIdAndDateBetween(Long accountId, LocalDateTime dateStart, LocalDateTime dateEnd) {
        return Arrays.asList(
                modelMapper.map(
                        transactionRepository.findAllByAccountIdAndDateBetween(accountId, dateStart, dateEnd),
                        TransactionDTO[].class)
        );
    }

    @Override
    @Transactional()
    public TransactionDTO updateTransaction(Long transactionId, TransactionDTO transactionDTO) {
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);

        Transaction existingTx = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionIdNotFoundException(TRANSACTION_ID_NOT_FOUND_IN_DB + transactionId));

        existingTx.setDate(transaction.getDate());
        existingTx.setType(transaction.getType());
        existingTx.setAmount(transaction.getAmount());
        existingTx.setBalance(transaction.getBalance());

        Transaction savedTx = transactionRepository.save(existingTx);

        return modelMapper.map(savedTx, TransactionDTO.class);
    }

    @Override
    @Transactional()
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository
                .findById(transactionId)
                .orElseThrow(() -> new TransactionIdNotFoundException(TRANSACTION_ID_NOT_FOUND_IN_DB + transactionId));

        Account account = transaction.getAccount();

        // Reverse the transaction and update the account balance
        Transaction trans = transaction.copyTx();
        transactionStContext.reverseTx(account, trans);
        accountRepository.save(account);

        // Create and save a new transaction for the reversal
        Transaction reverseTx = trans.copyTx();
        reverseTx.setType(getReverseTransactionType(transaction.getType()));
        transactionRepository.save(reverseTx);

        // Mark the original transaction as deleted
        transaction.setDeleted(true);
        transaction.setDeletedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    /**
     * Returns the reverse transaction type based on the original transaction type.
     *
     * @param type the original transaction type
     * @return the reverse transaction type
     */
    private TypeTx getReverseTransactionType(TypeTx type) {
        return switch (type) {
            case TypeTx.DEPOSIT -> TypeTx.WITHDRAWAL;
            case TypeTx.WITHDRAWAL -> TypeTx.DEPOSIT;
        };
    }

}
