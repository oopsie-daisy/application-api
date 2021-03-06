package com.oopsiedaisy.flowers.repository;

import com.oopsiedaisy.flowers.repository.entity.FlowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface FlowerJpaRepository extends JpaRepository<FlowerEntity, Integer>,
        JpaSpecificationExecutor<FlowerEntity> {

    void deleteAllByUuidIn(List<UUID> items);

    void deleteAllByIdIn(List<Integer> ids);

    List<FlowerEntity> findAllByUuidIn(List<UUID> uuids);

    List<FlowerEntity> findAllByUuid(UUID uuid);

    FlowerEntity findFirstByUuid(UUID uuid);

    int countAllByUuid(UUID uuid);
}
