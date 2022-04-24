package com.oopsiedaisy.config.exceptions;

import lombok.Value;

import java.util.Date;

@Value
public class ErrorResponse {

    String message;

    int status;

    Date createdAt;
}
