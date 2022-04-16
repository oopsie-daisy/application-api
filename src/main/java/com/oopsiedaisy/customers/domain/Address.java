package com.oopsiedaisy.customers.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Address {

    UUID uuid;

    String country;

    String city;

    String streetName;

    String houseNumber;

    String apartmentNumber;

    String zipCode;

}
