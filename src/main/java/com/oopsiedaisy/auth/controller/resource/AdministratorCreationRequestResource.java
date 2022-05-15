package com.oopsiedaisy.auth.controller.resource;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@Builder
public class AdministratorCreationRequestResource {

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String fullName;

    @NotBlank
    String phoneNumber;

    @NotBlank
    String email;

    @NotBlank
    String password;

}
