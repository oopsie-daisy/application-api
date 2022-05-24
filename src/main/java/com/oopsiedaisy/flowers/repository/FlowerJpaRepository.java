package com.oopsiedaisy.flowers.repository;

import com.oopsiedaisy.flowers.repository.entity.FlowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface FlowerJpaRepository extends JpaRepository<FlowerEntity, Integer>,
        JpaSpecificationExecutor<FlowerEntity> {

    void deleteAllByUuidIn(List<UUID> items);

    List<FlowerEntity> findAllByUuidIn(List<UUID> uuids);

    FlowerEntity findByUuid(UUID uuid);
}
