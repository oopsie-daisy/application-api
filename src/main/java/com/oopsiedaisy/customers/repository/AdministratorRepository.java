package com.oopsiedaisy.customers.repository;

import com.oopsiedaisy.customers.domain.Administrator;
import com.oopsiedaisy.customers.mapper.AdministratorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static java.util.UUID.fromString;

@Repository
@RequiredArgsConstructor
public class AdministratorRepository {

    private final AdministratorJpaRepository repository;

    private final AdministratorMapper mapper;

    public Administrator persist(Administrator administrator) {
        return mapper.toDomain(repository.save(mapper.toEntity(administrator)));
    }

    public Administrator getByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain)
                .orElse(null);
    }

    public Administrator getByUuid(String uuid) {
        return mapper.toDomain(repository.findByUuid(fromString(uuid)));
    }
}
