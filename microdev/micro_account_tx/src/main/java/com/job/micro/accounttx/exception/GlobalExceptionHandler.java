package com.job.micro.accounttx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountIdNotFoundException.class)
    public ResponseEntity<DetailedErrorResponse> handleAccountIdNotFoundException(AccountIdNotFoundException exception,
                                                                                  WebRequest webReq) {
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                LocalDateTime.now(),
                webReq.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNumberAlreadyExistsException.class)
    public ResponseEntity<DetailedErrorResponse> handleAccountNumberAlreadyExistsException(
            AccountNumberAlreadyExistsException exception,
            WebRequest webReq) {
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                LocalDateTime.now(),
                webReq.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientIdNotFoundException.class)
    public ResponseEntity<DetailedErrorResponse> handleClientIdNotFoundException(ClientIdNotFoundException exception,
                                                                                 WebRequest webReq) {
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                LocalDateTime.now(),
                webReq.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionIdNotFoundException.class)
    public ResponseEntity<DetailedErrorResponse> handleTransactionIdNotFoundException(
            TransactionIdNotFoundException exception,
            WebRequest webReq) {
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                LocalDateTime.now(),
                webReq.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnavailableBalanceException.class)
    public ResponseEntity<DetailedErrorResponse> handleUnavailableBalanceException(
            UnavailableBalanceException exception,
            WebRequest webRequest) {
        DetailedErrorResponse detailedErrorResponse = new DetailedErrorResponse(
                LocalDateTime.now(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );
        return new ResponseEntity<>(detailedErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
