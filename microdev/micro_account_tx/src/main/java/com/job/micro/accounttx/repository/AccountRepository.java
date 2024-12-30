package com.job.micro.accounttx.repository;

import com.job.micro.accounttx.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String numberAccount);

    @Override
    @Query("select a from Account a left join fetch a.client where a.id = ?1")
    Optional<Account> findById(Long id);

    @Query("select a from Account a left join fetch a.client")
    List<Account> findAllAccountJoinClient();

}
