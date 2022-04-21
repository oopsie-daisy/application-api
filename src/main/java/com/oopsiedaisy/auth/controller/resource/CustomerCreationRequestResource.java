package com.oopsiedaisy.auth.controller.resource;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Value
@Builder
public class CustomerCreationRequestResource {

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String fullName;

    @NotEmpty
    List<AddressCreationResource> addresses;

    @NotBlank
    String phoneNumber;

    @NotBlank
    String email;

    @NotBlank
    String password;

}
