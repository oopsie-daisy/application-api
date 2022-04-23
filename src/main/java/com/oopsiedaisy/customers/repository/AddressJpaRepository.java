package com.oopsiedaisy.customers.repository;

import com.oopsiedaisy.customers.repository.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<AddressEntity, Integer> {

}
