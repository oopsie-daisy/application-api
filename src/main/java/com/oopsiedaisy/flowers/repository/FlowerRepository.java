package com.oopsiedaisy.flowers.repository;

import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.flowers.mapper.FlowerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FlowerRepository {

    private final FlowerJpaRepository repository;

    private final FlowerMapper mapper;

    public List<Flower> getAll() {
        return mapper.toDomain(repository.findAll());
    }
}
