package com.oopsiedaisy.flowers.repository;

import com.oopsiedaisy.flowers.repository.entity.FlowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowerJpaRepository extends JpaRepository<FlowerEntity, Integer> {

}
