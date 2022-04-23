package com.oopsiedaisy.customers.repository;

import com.oopsiedaisy.customers.domain.Customer;
import com.oopsiedaisy.customers.mapper.CustomerMapper;
import com.oopsiedaisy.customers.repository.entity.AddressEntity;
import com.oopsiedaisy.customers.repository.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {

    private final CustomerJpaRepository repository;

    private final AddressJpaRepository addressJpaRepository;

    private final CustomerMapper mapper;

    public Customer persist(Customer customer) {
        CustomerEntity customerEntity = setCustomersAddresses(customer);
        CustomerEntity saved = repository.save(customerEntity);
        addressJpaRepository.saveAll(customerEntity.getAddresses());
        return mapper.toDomain(saved);
    }

    private CustomerEntity setCustomersAddresses(Customer customer) {
        CustomerEntity customerEntity = mapper.toEntity(customer);
        for (AddressEntity address : customerEntity.getAddresses()) {
            address.setCustomer(customerEntity);
        }
        return customerEntity;
    }

    public Customer getByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain)
                .orElse(null);
    }
}
