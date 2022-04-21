package com.oopsiedaisy.auth.controller.resource;

import lombok.Value;

import java.util.UUID;

@Value
public class AuthenticationResultResource {

    String jwt;

    UUID userUuid;

    AuthenticationStatus status;

    String errorMessage;

}
