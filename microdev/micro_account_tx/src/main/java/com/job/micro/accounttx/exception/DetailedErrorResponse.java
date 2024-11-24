package com.job.micro.accounttx.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailedErrorResponse {

    private LocalDateTime timestamp;
    private String apiPath;
    private HttpStatus errorCode;
    private String errorMessage;

}
