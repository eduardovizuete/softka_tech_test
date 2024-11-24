package com.job.micro.accounttx.exception;

public class UnavailableBalanceException extends RuntimeException {

    private String message;

    public UnavailableBalanceException(String message) {
        super(message);
    }

}
