package com.job.micro.accounttx.service;

import com.job.micro.accounttx.dto.AccountDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDTO);

    Mono<AccountDTO> createAccountAsync(AccountDTO accountDTO);

    List<AccountDTO> getAllAccounts();

    AccountDTO getAccountById(Long accountId);

    AccountDTO updateAccount(Long accountId, AccountDTO accountDTO);

    void deleteAccount(Long accountId);

}
