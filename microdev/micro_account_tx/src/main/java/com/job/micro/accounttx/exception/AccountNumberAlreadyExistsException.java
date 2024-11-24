package com.job.micro.accounttx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountNumberAlreadyExistsException extends RuntimeException {

    public AccountNumberAlreadyExistsException(String message) {
        super(message);
    }

}
