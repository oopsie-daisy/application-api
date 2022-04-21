package com.oopsiedaisy.auth.domain;

import com.oopsiedaisy.auth.controller.resource.AuthenticationStatus;
import lombok.Value;

import java.util.UUID;

@Value
public class AuthenticationResult {

    String jwt;

    UUID userUuid;

    AuthenticationStatus status;

    String errorMessage;

}
