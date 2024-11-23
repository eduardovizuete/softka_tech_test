package com.job.micro.personclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PersonIdNotFoundException extends RuntimeException {

    public PersonIdNotFoundException(String message) {
        super(message);
    }

}
