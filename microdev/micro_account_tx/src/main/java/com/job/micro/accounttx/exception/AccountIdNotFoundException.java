package com.job.micro.accounttx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountIdNotFoundException extends RuntimeException {

    public AccountIdNotFoundException(String message) {
        super(message);
    }

}
