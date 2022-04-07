package com.oopsiedaisy.customers.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Address {

    String country;

    String city;

    String streetName;

    String houseNumber;

    String apartmentNumber;

    String zipCode;

}
