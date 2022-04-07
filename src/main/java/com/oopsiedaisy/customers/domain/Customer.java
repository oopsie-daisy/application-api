package com.oopsiedaisy.customers.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Customer {

    String firstName;

    String lastName;

    String fullName;

    Address address;

    String phoneNumber;

    String email;

    String iban;

}
