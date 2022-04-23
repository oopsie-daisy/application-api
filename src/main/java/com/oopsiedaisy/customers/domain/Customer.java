package com.oopsiedaisy.customers.domain;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class Customer {

    UUID uuid;

    String firstName;

    String lastName;

    String fullName;

    List<Address> addresses;

    String phoneNumber;

    String email;

    String password;

}
