package com.oopsiedaisy.auth.controller.resource;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class AuthenticationRequestResource {

    @NotBlank
    String email;

    @NotBlank
    String password;
}
