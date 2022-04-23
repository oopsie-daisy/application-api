package com.oopsiedaisy.customers.repository;

import com.oopsiedaisy.customers.repository.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, Integer> {

    Optional<CustomerEntity> findByEmail(String email);

    CustomerEntity findByUuid(UUID uuid);
}
