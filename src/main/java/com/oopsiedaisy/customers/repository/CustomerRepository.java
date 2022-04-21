package com.oopsiedaisy.customers.repository;

import com.oopsiedaisy.customers.domain.Customer;
import com.oopsiedaisy.customers.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {

    private final CustomerJpaRepository repository;

    private final CustomerMapper mapper;

    public Customer getByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain)
                .orElse(null);
    }
}
