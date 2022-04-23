package com.oopsiedaisy.auth.controller;

import com.oopsiedaisy.auth.controller.resource.*;
import com.oopsiedaisy.auth.domain.CreationStatus;
import com.oopsiedaisy.auth.mapper.AuthenticationMapper;
import com.oopsiedaisy.auth.service.AuthenticationService;
import com.oopsiedaisy.customers.mapper.CustomerMapper;
import com.oopsiedaisy.customers.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

import static com.oopsiedaisy.auth.controller.resource.AuthenticationStatus.FAILED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private static final String USER_EXISTS_ERROR = "User with that email already exists";

    private final AuthenticationService authenticationService;

    private final AuthenticationMapper mapper;

    private final CustomerService customerService;

    private final CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<AuthenticationResultResource> authenticateUser(
            @RequestBody @Valid AuthenticationRequestResource authenticationRequestResource) {
        AuthenticationResultResource result = mapper.toResource(authenticationService.authenticate(mapper.toDomain(authenticationRequestResource)));
        if (FAILED == result.getStatus()) {
            return new ResponseEntity<>(result, UNAUTHORIZED);
        }
        return ok(result);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<CustomerCreationResponse> createCustomer(
            @RequestBody @Valid CustomerCreationRequestResource customerCreationResource) {
        CustomerResource createdCustomer = customerMapper.toResource(customerService.create(customerMapper.toDomain(customerCreationResource)));
        if (createdCustomer == null) {
            return badRequest().body(buildCreationResponse(null, USER_EXISTS_ERROR, CreationStatus.FAILED));
        }
        URI location = fromCurrentRequest()
                .path("/customer/{id}")
                .buildAndExpand(createdCustomer.getUuid())
                .toUri();
        return created(location).body(buildCreationResponse(createdCustomer.getUuid(), null, CreationStatus.OK));
    }

    private CustomerCreationResponse buildCreationResponse(UUID uuid, String errorMessage, CreationStatus status) {
        return new CustomerCreationResponse(uuid, errorMessage, status);
    }
}
