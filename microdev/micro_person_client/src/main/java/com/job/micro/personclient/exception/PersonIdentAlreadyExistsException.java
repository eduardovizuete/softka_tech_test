package com.job.micro.personclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonIdentAlreadyExistsException extends RuntimeException {

    public PersonIdentAlreadyExistsException(String message) {
        super(message);
    }

}
