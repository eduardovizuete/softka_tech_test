package com.job.micro.personclient.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PersonIdNotFoundException.class)
    public ResponseEntity<DetailedErrorResponse> handlePersonIdNotFoundException(PersonIdNotFoundException exception, WebRequest webReq) {
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                LocalDateTime.now(),
                webReq.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonIdentAlreadyExistsException.class)
    public ResponseEntity<DetailedErrorResponse> handlePersonIdentAlreadyExistsException(PersonIdentAlreadyExistsException exception, WebRequest webReq) {
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                LocalDateTime.now(),
                webReq.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientIdAlreadyExistsException.class)
    public ResponseEntity<DetailedErrorResponse> handleClientByClientIdAlreadyExistsException(ClientIdAlreadyExistsException exception, WebRequest webReq) {
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                LocalDateTime.now(),
                webReq.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientIdNotFoundException.class)
    public ResponseEntity<DetailedErrorResponse> handleClientIdNotFoundException(ClientIdNotFoundException exception, WebRequest webReq) {
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                LocalDateTime.now(),
                webReq.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.NOT_FOUND);
    }

}
