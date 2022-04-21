package com.oopsiedaisy.flowers.repository;

import com.oopsiedaisy.flowers.controller.util.FlowerFilter;
import com.oopsiedaisy.flowers.repository.entity.FlowerEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class FlowersSpecification {

    public Specification<FlowerEntity> searchByFilter(FlowerFilter filter) {
        return (root, query, criteriaBuilder) -> toPredicate(filter, root, criteriaBuilder);
    }

    private Predicate toPredicate(FlowerFilter filter, Root<FlowerEntity> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter == null) {
            return criteriaBuilder.or();
        }
        if (isNotBlank(filter.getTitle())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + filter.getTitle().toLowerCase() + "%"));
        }
        if (filter.getBouquet() != null) {
            predicates.add(criteriaBuilder.equal(root.get("bouquet"), filter.getBouquet()));
        }
        if (isNotBlank(filter.getColor())) {
            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("baseColor")), filter.getColor().toLowerCase()));
        }
        if (filter.getPriceFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getPriceFrom()));
        }
        if (filter.getPriceTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getPriceTo()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
