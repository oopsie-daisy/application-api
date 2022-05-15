package com.oopsiedaisy.auth.controller.resource;

import com.oopsiedaisy.auth.domain.CreationStatus;
import lombok.Value;

import java.util.UUID;

@Value
public class AdministratorCreationResponse {

    UUID administratorUuid;

    String errorMessage;

    CreationStatus status;
}
