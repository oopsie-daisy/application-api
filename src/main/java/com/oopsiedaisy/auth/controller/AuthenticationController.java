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

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final AuthenticationMapper mapper;

    private final CustomerService customerService;

    private final CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<AuthenticationResultResource> authenticateUser(
            @RequestBody @Valid AuthenticationRequestResource authenticationRequestResource) {
        AuthenticationResultResource result = mapper.toResource(authenticationService.authenticate(mapper.toDomain(authenticationRequestResource)));
        return ok(result);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<CustomerCreationResponse> createCustomer(
            @RequestBody @Valid CustomerCreationRequestResource customerCreationResource) {
        CustomerResource createdCustomer = customerMapper.toResource(customerService.signUpCustomer(customerMapper.toDomain(customerCreationResource)));
        URI location = fromCurrentRequest()
                .path("/customer/{id}")
                .buildAndExpand(createdCustomer.getUuid())
                .toUri();
        return created(location).body(new CustomerCreationResponse(createdCustomer.getUuid(), null, CreationStatus.OK));
    }
}
