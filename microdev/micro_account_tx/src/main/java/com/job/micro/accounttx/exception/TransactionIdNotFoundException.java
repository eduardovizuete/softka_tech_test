package com.job.micro.accounttx.exception;

public class TransactionIdNotFoundException extends RuntimeException {

    public TransactionIdNotFoundException(String message) {
        super(message);
    }

}
