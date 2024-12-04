package com.job.micro.personclient.exception;

import lombok.Data;

@Data
public class ValidationError {

    private final String field;
    private final String message;

}
