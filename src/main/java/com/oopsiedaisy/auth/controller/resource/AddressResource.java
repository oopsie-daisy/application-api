package com.oopsiedaisy.auth.controller.resource;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class AddressResource {

    @NotBlank
    String country;

    @NotBlank
    String city;

    @NotBlank
    String streetName;

    @NotBlank
    String houseNumber;

    String apartmentNumber;

    @NotBlank
    String zipCode;
}
