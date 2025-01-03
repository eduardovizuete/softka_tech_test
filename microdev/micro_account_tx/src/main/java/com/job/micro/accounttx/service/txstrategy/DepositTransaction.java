package com.job.micro.accounttx.service.txstrategy;

import com.job.micro.accounttx.entity.Account;
import com.job.micro.accounttx.entity.Transaction;
import com.job.micro.accounttx.exception.UnavailableBalanceException;
import org.springframework.stereotype.Component;

@Component("DEPOSIT")
public class DepositTransaction implements TransactionStrategy {

    public static final String BALANCE_UNAVAILABLE = "Balance unavailable";

    @Override
    public void executeTx(Account account, Transaction transaction) {
        updatePreTxData(account, transaction);
        account.setBalance(account.getBalance() + transaction.getAmount());
        updatePostTxData(account, transaction);
    }

    @Override
    public void reverseTx(Account account, Transaction transaction) {
        if (account.getBalance() < transaction.getAmount()) {
            throw new UnavailableBalanceException(BALANCE_UNAVAILABLE);
        }

        updatePreTxData(account, transaction);
        account.setBalance(account.getBalance() - transaction.getAmount());
        updatePostTxData(account, transaction);
    }

}
