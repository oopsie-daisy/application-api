package com.oopsiedaisy.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {NotAuthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse notAuthorised(Exception ex) {
        return new ErrorResponse(ex.getMessage(), 401, new Date());
    }

    @ExceptionHandler(value = {FailedPaymentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequest(Exception ex) {
        return new ErrorResponse(ex.getMessage(), 400, new Date());
    }

    @ExceptionHandler(value = {FailedToSendEmailException.class})
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorResponse badGateway(Exception ex) {
        return new ErrorResponse(ex.getMessage(), 502, new Date());
    }

}
