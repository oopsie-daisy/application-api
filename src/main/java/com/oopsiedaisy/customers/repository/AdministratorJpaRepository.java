package com.oopsiedaisy.customers.repository;

import com.oopsiedaisy.customers.repository.entity.AdministratorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdministratorJpaRepository extends JpaRepository<AdministratorEntity, Integer> {

    Optional<AdministratorEntity> findByEmail(String email);

    AdministratorEntity findByUuid(UUID uuid);
}
