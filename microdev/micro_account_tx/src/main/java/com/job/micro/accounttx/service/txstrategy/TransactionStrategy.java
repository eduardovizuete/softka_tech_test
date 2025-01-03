package com.job.micro.accounttx.service.txstrategy;

import com.job.micro.accounttx.entity.Account;
import com.job.micro.accounttx.entity.Transaction;

import java.time.LocalDateTime;

public interface TransactionStrategy {

    void executeTx(Account account, Transaction transaction);

    void reverseTx(Account account, Transaction transaction);

    default void updatePreTxData(Account account, Transaction transaction) {
        transaction.setAccount(account);
        transaction.setBalanceBeforeTx(account.getBalance());
    }

    default void updatePostTxData(Account account, Transaction transaction) {
        transaction.setDate(LocalDateTime.now());
        transaction.setBalance(account.getBalance());
    }

}
