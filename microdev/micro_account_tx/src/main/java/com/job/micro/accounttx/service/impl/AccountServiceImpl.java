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
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    public static final String ACCOUNT_ID_NOT_FOUND_IN_DB = "Account id not found in db! : ";
    public static final String CLIENT_ID_NOT_FOUND_IN_DB = "Client id not found in db! : ";
    private static final String URL_REGISTER_MICROPC_SERVICE = "http://micropc-service";
    private static final String API_CLIENTS = "/api/clients/";
    private static final String ACCOUNT_NUMBER_ALREADY_EXISTS_IN_DB = "Account number already exists in db! : ";
    private static final String ERROR_FETCHING_CLIENT_INFO = "Error fetching client info";

    private AccountRepository accountRepository;
    private WebClient webClient;
    private WebClient.Builder loadBalancedWebClient;
    private ModelMapper modelMapper;

    @Override
    @Transactional()
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);
        findAccountByNumber(account);

        ClientDTO clientDTO = webClient
                .get()
                .uri(API_CLIENTS + account.getClient().getClientId())
                .retrieve()
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        clientResponse -> Mono.error(
                                new ClientIdNotFoundException(
                                        CLIENT_ID_NOT_FOUND_IN_DB + account.getClient().getClientId()))
                )
                .bodyToMono(ClientDTO.class)
                .block();

        account.setClient(modelMapper.map(clientDTO, Client.class));

        Account savedAccount = accountRepository.save(account);
        return modelMapper.map(savedAccount, AccountDTO.class);
    }

    @CircuitBreaker(name = "createAccountAsyncCircuitBreaker", fallbackMethod = "fallBackFetchClientInfo")
    @Override
    @Transactional()
    public Mono<AccountDTO> createAccountAsync(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);
        findAccountByNumber(account);

        // Perform the asynchronous GET call
        Mono<ClientDTO> responseClient = loadBalancedWebClient
                .build()
                .get()
                .uri(URL_REGISTER_MICROPC_SERVICE + API_CLIENTS + account.getClient().getClientId())
                .retrieve()
                .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        response -> Mono.error(
                                new ClientIdNotFoundException(
                                        CLIENT_ID_NOT_FOUND_IN_DB + account.getClient().getClientId()))
                )
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
                modelMapper.map(accountRepository.findAllAccountJoinClient(), AccountDTO[].class)
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

    private void findAccountByNumber(Account account) {
        if (accountRepository.findByNumber(account.getNumber()).isPresent()) {
            throw new AccountNumberAlreadyExistsException(
                    ACCOUNT_NUMBER_ALREADY_EXISTS_IN_DB + account.getNumber());
        }
    }

    public Mono<AccountDTO> fallBackFetchClientInfo(Exception exception) {
        LOG.error(ERROR_FETCHING_CLIENT_INFO + " {}", exception.getMessage());
        return Mono.error(new RuntimeException(ERROR_FETCHING_CLIENT_INFO, exception));
    }

}
