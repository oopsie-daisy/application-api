package com.oopsiedaisy.customers.service;

import com.oopsiedaisy.customers.domain.Customer;
import com.oopsiedaisy.customers.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Customer create(Customer customerCreationResource) {
        if (customerExistsWithEmail(customerCreationResource.getEmail())) {
            return null;
        }
        return repository.persist(customerCreationResource);
    }

    private boolean customerExistsWithEmail(String email) {
        return repository.getByEmail(email) != null;
    }
}
