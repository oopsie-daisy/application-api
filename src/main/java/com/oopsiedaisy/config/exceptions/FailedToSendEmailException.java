package com.oopsiedaisy.config.exceptions;

public class FailedToSendEmailException extends RuntimeException {
    public FailedToSendEmailException(String message) {
        super(message);
    }
}
