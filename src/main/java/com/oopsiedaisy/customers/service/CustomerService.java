package com.oopsiedaisy.customers.service;

import com.oopsiedaisy.config.exceptions.NotAuthorizedException;
import com.oopsiedaisy.customers.domain.Customer;
import com.oopsiedaisy.customers.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final String USER_EXISTS_ERROR = "User with that email already exists";

    private final CustomerRepository repository;

    public Customer signUpCustomer(Customer customerCreationResource) {
        if (customerExistsWithEmail(customerCreationResource.getEmail())) {
            throw new NotAuthorizedException(USER_EXISTS_ERROR);
        }
        String hashedPassword = hashPassword(customerCreationResource.getPassword());
        customerCreationResource.setPassword(hashedPassword);
        return repository.persist(customerCreationResource);
    }

    private boolean customerExistsWithEmail(String email) {
        return repository.getByEmail(email) != null;
    }

    public Customer getCustomer(String uuid) {
        return repository.getByUuid(uuid);
    }

    private static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
}
