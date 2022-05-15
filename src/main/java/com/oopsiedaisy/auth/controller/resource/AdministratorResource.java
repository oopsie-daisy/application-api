package com.oopsiedaisy.auth.controller.resource;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class AdministratorResource {

    @NotBlank
    UUID uuid;

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
}
