package com.job.micro.accounttx.exception;

public class AccountNumberAlreadyExistsException extends RuntimeException {

    public AccountNumberAlreadyExistsException(String message) {
        super(message);
    }

}
