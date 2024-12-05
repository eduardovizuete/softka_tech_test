package com.job.micro.personclient.exception;

public class PersonIdentAlreadyExistsException extends RuntimeException {

    public PersonIdentAlreadyExistsException(String message) {
        super(message);
    }

}
