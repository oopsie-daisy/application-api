package com.oopsiedaisy.auth.controller;

import com.oopsiedaisy.auth.controller.resource.AuthenticationRequestResource;
import com.oopsiedaisy.auth.controller.resource.AuthenticationResultResource;
import com.oopsiedaisy.auth.domain.AuthenticationResult;
import com.oopsiedaisy.auth.mapper.AuthenticationMapper;
import com.oopsiedaisy.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.oopsiedaisy.auth.controller.resource.AuthenticationStatus.FAILED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final AuthenticationMapper mapper;

    @PostMapping
    public ResponseEntity<AuthenticationResultResource> authenticateUser(
            @RequestBody @Valid AuthenticationRequestResource authenticationRequestResource) {
        AuthenticationResultResource result = mapper.toResource(authenticationService.authenticate(mapper.toDomain(authenticationRequestResource)));
        if (FAILED == result.getStatus()) {
            return new ResponseEntity<>(result, UNAUTHORIZED);
        }
        return ok(result);
    }
}
