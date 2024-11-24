package com.job.micro.accounttx.controller;

import com.job.micro.accounttx.dto.AccountDTO;
import com.job.micro.accounttx.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Tag(
        name = "Account API service - AccountController",
        description = "AccountController exposes REST APIs for account service"
)

@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
@Transactional
public class AccountController {

    private AccountService accountService;

    @Operation(
            summary = "Save account REST API",
            description = "Save account REST API is used to save an account object in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 Created"
    )
    // build create account REST API
    // http://localhost:8080/api/account/
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO, UriComponentsBuilder uriComponentsBuilder) {
        AccountDTO savedAccount = accountService.createAccount(accountDTO);

        URI locationURI = uriComponentsBuilder
                .path("/api/accounts/" + savedAccount.getId())
                .buildAndExpand(uriComponentsBuilder.toUriString())
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(locationURI)
                .body(savedAccount);
    }

    @Operation(
            summary = "Save asynchronous account REST API",
            description = "Save asynchronous account REST API is used to save an account object in a database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status 201 Created"
    )
    // build create account REST API asynchronous
    // http://localhost:8080/api/account/createAsync
    @PostMapping("/createAsync")
    public Mono<ResponseEntity<AccountDTO>> createAccountAsync(@RequestBody AccountDTO accountDTO, UriComponentsBuilder uriComponentsBuilder) {
        Mono<AccountDTO> savedAccount = accountService.createAccountAsync(accountDTO);
        return savedAccount.map(acc -> {
            URI locationURI = uriComponentsBuilder
                    .path("/api/accounts/" + acc.getId())
                    .buildAndExpand(uriComponentsBuilder.toUriString())
                    .toUri();

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .location(locationURI)
                    .body(acc);
        });
    }

    @Operation(
            summary = "Get all accounts REST API",
            description = "Get all accounts REST API is used to get all accounts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build get all accounts by id REST API
    // http://localhost:8080/api/accounts
    @GetMapping()
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @Operation(
            summary = "Get account REST API",
            description = "Get accounts REST API is used to get an account from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build get account by id REST API
    // http://localhost:8080/api/accounts/1
    @GetMapping("{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("id") Long accountId) {
        AccountDTO account = accountService.getAccountById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @Operation(
            summary = "Update account REST API",
            description = "Update account REST API is used to update an account from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build update account REST API
    // http://localhost:8080/api/accounts/1
    @PutMapping("{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable("id") Long accountId,
                                                 @RequestBody AccountDTO accountDTO) {
        AccountDTO updatedAccount = accountService.updateAccount(accountId, accountDTO);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete account REST API",
            description = "Delete account REST API is used to delete an account from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // build delete account REST API
    // http://localhost:8080/api/accounts/1
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long accountId) {
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>("Account successfully deleted!", HttpStatus.OK);
    }

}
