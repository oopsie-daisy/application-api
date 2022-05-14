package com.oopsiedaisy.customers.service;

import com.oopsiedaisy.config.exceptions.NotAuthorizedException;
import com.oopsiedaisy.customers.domain.Administrator;
import com.oopsiedaisy.customers.repository.AdministratorRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministratorService {

    private static final String USER_EXISTS_ERROR = "User with that email already exists";

    private final AdministratorRepository repository;

    public Administrator signUpAdministrator(Administrator administratorCreationResource) {
        if (customerExistsWithEmail(administratorCreationResource.getEmail())) {
            throw new NotAuthorizedException(USER_EXISTS_ERROR);
        }
        String hashedPassword = hashPassword(administratorCreationResource.getPassword());
        administratorCreationResource.setPassword(hashedPassword);
        return repository.persist(administratorCreationResource);
    }

    private boolean customerExistsWithEmail(String email) {
        return repository.getByEmail(email) != null;
    }

    public Administrator getCustomer(String uuid) {
        return repository.getByUuid(uuid);
    }

    private static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
}
