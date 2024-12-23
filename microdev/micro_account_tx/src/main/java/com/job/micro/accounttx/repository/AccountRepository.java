package com.job.micro.accounttx.repository;

import com.job.micro.accounttx.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String numberAccount);
}
