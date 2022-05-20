package com.oopsiedaisy.config.exceptions;

public class FailedPaymentException extends RuntimeException {

    public FailedPaymentException(String message) {
        super(message);
    }
}
