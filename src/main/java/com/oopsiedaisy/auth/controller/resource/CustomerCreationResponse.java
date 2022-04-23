package com.oopsiedaisy.auth.controller.resource;

import com.oopsiedaisy.auth.domain.CreationStatus;
import lombok.Value;

import java.util.UUID;

@Value
public class CustomerCreationResponse {

    UUID customerUuid;

    String errorMessage;

    CreationStatus status;
}
