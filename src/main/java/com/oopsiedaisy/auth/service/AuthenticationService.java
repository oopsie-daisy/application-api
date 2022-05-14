package com.oopsiedaisy.auth.service;

import com.oopsiedaisy.auth.domain.AuthenticationRequest;
import com.oopsiedaisy.auth.domain.AuthenticationResult;
import com.oopsiedaisy.config.exceptions.NotAuthorizedException;
import com.oopsiedaisy.customers.domain.Customer;
import com.oopsiedaisy.customers.repository.CustomerRepository;
import com.oopsiedaisy.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import static com.oopsiedaisy.auth.controller.resource.AuthenticationStatus.OK;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String CUSTOMER_NOT_FOUND = "Customer with this email not found";
    private static final String BAD_CREDENTIALS = "Bad credentials";

    private final CustomerRepository customerRepository;
    private final JwtService jwtService;

    public AuthenticationResult authenticate(AuthenticationRequest authenticationRequest) {
        Customer foundCustomer = customerRepository.getByEmail(authenticationRequest.getEmail());
        if (isNull(foundCustomer)) {
            throw new NotAuthorizedException(CUSTOMER_NOT_FOUND);
        }
        if (isPasswordNotCorrect(authenticationRequest, foundCustomer)) {
            throw new NotAuthorizedException(BAD_CREDENTIALS);
        }
        return buildSuccessfulAuthenticationResult(foundCustomer);
    }

    private boolean isPasswordNotCorrect(AuthenticationRequest authenticationRequest, Customer foundCustomer) {
        return !BCrypt.checkpw(authenticationRequest.getPassword(), foundCustomer.getPassword());
    }

    private AuthenticationResult buildSuccessfulAuthenticationResult(Customer foundCustomer) {
        String jwt = jwtService.generateToken(foundCustomer.getUuid());
        return new AuthenticationResult(jwt, foundCustomer.getUuid(), OK, null);
    }
}
