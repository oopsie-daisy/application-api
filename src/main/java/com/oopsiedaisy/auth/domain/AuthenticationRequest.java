package com.oopsiedaisy.auth.domain;

import lombok.Value;

@Value
public class AuthenticationRequest {

    String email;

    String password;
}
