package com.oopsiedaisy.flowers.repository;

import com.oopsiedaisy.config.exceptions.FailedPaymentException;
import com.oopsiedaisy.flowers.controller.util.FlowerFilter;
import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.flowers.mapper.FlowerMapper;
import com.oopsiedaisy.flowers.repository.entity.FlowerEntity;
import com.oopsiedaisy.payments.controller.resource.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.UUID;

import static com.oopsiedaisy.payments.controller.resource.PaymentStatus.COMPLETED;
import static org.springframework.data.domain.Sort.by;

@Repository
@RequiredArgsConstructor
public class FlowerRepository {

    private static final String FAILED_PAYMENT = "Failed to complete payment: ";

    private final FlowerJpaRepository repository;

    private final FlowersSpecification specification;

    private final FlowerMapper mapper;

    public List<Flower> getByUuids(List<UUID> uuids) {
        return mapper.toDomain(repository.findAllByUuidIn(uuids));
    }

    public List<Flower> getAll() {
        return mapper.toDomain(repository.findAll());

    }

    public List<Flower> getAll(FlowerFilter filter) {
        Sort sort = by("price").descending();

        Specification<FlowerEntity> criteria = specification.searchByFilter(filter);
        return mapper.toDomain(repository.findAll(criteria, sort));
    }

    public List<Flower> addAll(List<Flower> flowersToAdd) {
        return mapper.toDomain(repository.saveAll(mapper.toEntity(flowersToAdd)));
    }

    public PaymentStatus removeAll(List<UUID> items) {
        try {
            repository.deleteAllByUuidIn(items);
            return COMPLETED;
        } catch (OptimisticLockException e) {
            throw new FailedPaymentException(FAILED_PAYMENT + e.getMessage());
        }
    }
}
