package com.job.micro.accounttx.exception;

public class UnavailableBalanceException extends RuntimeException {

    public UnavailableBalanceException(String message) {
        super(message);
    }

}
