package com.job.micro.personclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientIdAlreadyExistsException extends RuntimeException {

    public ClientIdAlreadyExistsException(String message) {
        super(message);
    }

}
