package com.job.micro.accounttx.service.txstrategy;

import com.job.micro.accounttx.entity.enumeration.TypeTx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TransactionStrategyFactory {

    private static final String NO_STRATEGY_FOUND_FOR_TRANSACTION_TYPE = "No strategy found for transaction type: ";
    private final Map<String, TransactionStrategy> strategies;

    @Autowired
    public TransactionStrategyFactory(Map<String, TransactionStrategy> strategies) {
        this.strategies = strategies;
    }

    /**
     * Retrieves the appropriate TransactionStrategy based on the transaction type
     *
     * @param type the type of transaction (e.g., "DEPOSIT", "WITHDRAWAL")
     * @return the corresponding TransactionStrategy implementation
     * @throws IllegalArgumentException if no strategy matches the type
     */
    public TransactionStrategy getTransactionStrategy(TypeTx type) {
        TransactionStrategy strategy = strategies.get(type.name());
        if (strategy == null) {
            throw new IllegalArgumentException(NO_STRATEGY_FOUND_FOR_TRANSACTION_TYPE + type);
        }
        return strategy;
    }

}
