package com.job.micro.accounttx.service.impl;

import com.job.micro.accounttx.dto.AccountDTO;
import com.job.micro.accounttx.dto.ClientDTO;
import com.job.micro.accounttx.entity.Account;
import com.job.micro.accounttx.entity.Client;
import com.job.micro.accounttx.exception.AccountIdNotFoundException;
import com.job.micro.accounttx.exception.AccountNumberAlreadyExistsException;
import com.job.micro.accounttx.exception.ClientIdNotFoundException;
import com.job.micro.accounttx.repository.AccountRepository;
import com.job.micro.accounttx.service.AccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    public static final String ACCOUNT_ID_NOT_FOUND_IN_DB = "Account id not found in db! : ";
    public static final String CLIENT_ID_NOT_FOUND_IN_DB = "Client id not found in db! : ";
    private static final String API_CLIENTS = "http://micropc:8080/api/clients/";

    private AccountRepository accountRepository;
    private WebClient webClient;
    private ModelMapper modelMapper;

    @Override
    @Transactional()
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);

        if (accountRepository.findByNumber(account.getNumber()).isPresent()) {
            throw new AccountNumberAlreadyExistsException(
                    "Account number already exists in db! : " + account.getNumber());
        }

        Optional<ClientDTO> client = Optional.ofNullable(
                webClient.get()
                        .uri(API_CLIENTS + account.getClient().getClientId())
                        .retrieve()
                        .bodyToMono(ClientDTO.class)
                        .block()
        );

        if (client.isEmpty()) {
            throw new ClientIdNotFoundException(CLIENT_ID_NOT_FOUND_IN_DB + account.getClient().getClientId());
        } else {
            account.setClient(modelMapper.map(client.get(), Client.class));
        }

        Account savedAccount = accountRepository.save(account);
        return modelMapper.map(savedAccount, AccountDTO.class);
    }

    @Override
    @Transactional()
    public Mono<AccountDTO> createAccountAsync(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);

        if (accountRepository.findByNumber(account.getNumber()).isPresent()) {
            throw new AccountNumberAlreadyExistsException(
                    "Account number already exists in db! : " + account.getNumber());
        }

        // Perform the asynchronous GET call
        Mono<ClientDTO> responseClient = webClient.get()
                .uri(API_CLIENTS + account.getClient().getClientId())
                .retrieve()
                .bodyToMono(ClientDTO.class);

        return responseClient.map(respAsyncClient -> {
            account.setClient(modelMapper.map(respAsyncClient, Client.class));
            Account savedAccount = accountRepository.save(account);
            return modelMapper.map(savedAccount, AccountDTO.class);
        });
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return Arrays.asList(
                modelMapper.map(accountRepository.findAll(), AccountDTO[].class)
        );
    }

    @Override
    public AccountDTO getAccountById(Long accountId) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountIdNotFoundException(ACCOUNT_ID_NOT_FOUND_IN_DB + accountId));
        return modelMapper.map(account, AccountDTO.class);
    }

    @Override
    @Transactional()
    public AccountDTO updateAccount(Long accountId, AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);

        Account existingAccount = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountIdNotFoundException(ACCOUNT_ID_NOT_FOUND_IN_DB + accountId));

        existingAccount.setNumber(account.getNumber());
        existingAccount.setType(account.getType());
        existingAccount.setBalance(account.getBalance());
        existingAccount.setStatus(account.getStatus());

        Account savedAccount = accountRepository.save(existingAccount);
        return modelMapper.map(savedAccount, AccountDTO.class);
    }

    @Override
    @Transactional()
    public void deleteAccount(Long accountId) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountIdNotFoundException(ACCOUNT_ID_NOT_FOUND_IN_DB + accountId));
        accountRepository.deleteById(account.getId());
    }

}
