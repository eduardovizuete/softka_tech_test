package com.job.micro.accounttx.service.txstrategy;

import com.job.micro.accounttx.entity.Account;
import com.job.micro.accounttx.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionStContext {

    private final TransactionStrategyFactory factory;

    @Autowired
    public TransactionStContext(TransactionStrategyFactory factory) {
        this.factory = factory;
    }

    /**
     * Execute the perform operation.
     */
    public void executeTx(Account account, Transaction transaction) {
        TransactionStrategy txStrategy = factory.getTransactionStrategy(transaction.getType());
        txStrategy.executeTx(account, transaction);
    }

    /**
     * Execute the reverse operation.
     */
    public void reverseTx(Account account, Transaction transaction) {
        TransactionStrategy txStrategy = factory.getTransactionStrategy(transaction.getType());
        txStrategy.reverseTx(account, transaction);
    }

}
