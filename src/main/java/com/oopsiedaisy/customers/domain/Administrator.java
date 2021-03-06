package com.oopsiedaisy.customers.domain;

import lombok.Builder;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class Administrator {

    UUID uuid;

    String firstName;

    String lastName;

    String fullName;

    String phoneNumber;

    String email;

    @NonFinal
    @Setter
    String password;

}
