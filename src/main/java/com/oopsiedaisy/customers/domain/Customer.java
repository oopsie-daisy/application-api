package com.oopsiedaisy.customers.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Customer {

    UUID uuid;

    String firstName;

    String lastName;

    String fullName;

    Address address;

    String phoneNumber;

    String email;

    String iban;

    String password

}
