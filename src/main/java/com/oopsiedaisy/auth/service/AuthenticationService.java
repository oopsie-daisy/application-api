package com.oopsiedaisy.auth.service;

import com.oopsiedaisy.auth.domain.AuthenticationRequest;
import com.oopsiedaisy.auth.domain.AuthenticationResult;
import com.oopsiedaisy.config.exceptions.NotAuthorizedException;
import com.oopsiedaisy.customers.domain.Administrator;
import com.oopsiedaisy.customers.repository.AdministratorRepository;
import com.oopsiedaisy.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import static com.oopsiedaisy.auth.controller.resource.AuthenticationStatus.OK;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String ADMIN_NOT_FOUND = "Administrator with this email not found";
    private static final String BAD_CREDENTIALS = "Bad credentials";

    private final AdministratorRepository administratorRepository;
    private final JwtService jwtService;

    public AuthenticationResult authenticate(AuthenticationRequest authenticationRequest) {
        Administrator foundAdministrator = administratorRepository.getByEmail(authenticationRequest.getEmail());
        if (isNull(foundAdministrator)) {
            throw new NotAuthorizedException(ADMIN_NOT_FOUND);
        }
        if (isPasswordNotCorrect(authenticationRequest, foundAdministrator)) {
            throw new NotAuthorizedException(BAD_CREDENTIALS);
        }
        return buildSuccessfulAuthenticationResult(foundAdministrator);
    }

    private boolean isPasswordNotCorrect(AuthenticationRequest authenticationRequest, Administrator foundAdministrator) {
        return !BCrypt.checkpw(authenticationRequest.getPassword(), foundAdministrator.getPassword());
    }

    private AuthenticationResult buildSuccessfulAuthenticationResult(Administrator foundAdministrator) {
        String jwt = jwtService.generateToken(foundAdministrator.getUuid());
        return new AuthenticationResult(jwt, foundAdministrator.getUuid(), OK, null);
    }
}
