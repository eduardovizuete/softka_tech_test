package com.job.micro.accounttx.repository;

import com.job.micro.accounttx.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByAccountId(Long accountId);

    List<Transaction> findAllByAccountIdAndDateBetween(Long accountId, Instant dateStart, Instant dateEnd);

}
