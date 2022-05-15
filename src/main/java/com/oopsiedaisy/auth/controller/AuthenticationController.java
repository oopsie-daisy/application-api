package com.oopsiedaisy.auth.controller;

import com.oopsiedaisy.auth.controller.resource.*;
import com.oopsiedaisy.auth.domain.CreationStatus;
import com.oopsiedaisy.auth.mapper.AuthenticationMapper;
import com.oopsiedaisy.auth.service.AuthenticationService;
import com.oopsiedaisy.customers.mapper.AdministratorMapper;
import com.oopsiedaisy.customers.service.AdministratorService;
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

    private final AdministratorService administratorService;

    private final AdministratorMapper administratorMapper;

    @PostMapping
    public ResponseEntity<AuthenticationResultResource> authenticateUser(
            @RequestBody @Valid AuthenticationRequestResource authenticationRequestResource) {
        AuthenticationResultResource result = mapper.toResource(authenticationService.authenticate(mapper.toDomain(authenticationRequestResource)));
        return ok(result);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AdministratorCreationResponse> createCustomer(
            @RequestBody @Valid AdministratorCreationRequestResource customerCreationResource) {
        AdministratorResource createdCustomer = administratorMapper.toResource(administratorService.signUpAdministrator(administratorMapper.toDomain(customerCreationResource)));
        URI location = fromCurrentRequest()
                .path("/customer/{id}")
                .buildAndExpand(createdCustomer.getUuid())
                .toUri();
        return created(location).body(new AdministratorCreationResponse(createdCustomer.getUuid(), null, CreationStatus.OK));
    }
}
