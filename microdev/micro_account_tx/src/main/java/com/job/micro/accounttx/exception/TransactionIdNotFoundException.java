package com.job.micro.accounttx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TransactionIdNotFoundException extends RuntimeException {

    public TransactionIdNotFoundException(String message) {
        super(message);
    }

}
