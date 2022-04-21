package com.oopsiedaisy.auth.controller.resource;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class CustomerResource {

    @NotBlank
    UUID uuid;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String fullName;

    @NotEmpty
    List<AddressResource> addresses;

    @NotBlank
    String phoneNumber;

    @NotBlank
    String email;
}
