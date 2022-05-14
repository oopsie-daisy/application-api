package com.oopsiedaisy.flowers.repository;

import com.oopsiedaisy.flowers.controller.util.FlowerFilter;
import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.flowers.mapper.FlowerMapper;
import com.oopsiedaisy.flowers.repository.entity.FlowerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.domain.Sort.by;

@Repository
@RequiredArgsConstructor
public class FlowerRepository {

    private final FlowerJpaRepository repository;

    private final FlowersSpecification specification;

    private final FlowerMapper mapper;

    public List<Flower> getAll(FlowerFilter filter) {
        Sort sort = by("price").descending();

        Specification<FlowerEntity> criteria = specification.searchByFilter(filter);
        return mapper.toDomain(repository.findAll(criteria, sort));
    }

    public List<Flower> addAll(List<Flower> flowersToAdd) {
        return mapper.toDomain(repository.saveAll(mapper.toEntity(flowersToAdd)));
    }
}
