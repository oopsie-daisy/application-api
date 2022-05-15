package com.oopsiedaisy.customers.mapper;

import com.oopsiedaisy.auth.controller.resource.AdministratorCreationRequestResource;
import com.oopsiedaisy.auth.controller.resource.AdministratorResource;
import com.oopsiedaisy.config.MapStructConfig;
import com.oopsiedaisy.customers.domain.Administrator;
import com.oopsiedaisy.customers.repository.entity.AdministratorEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface AdministratorMapper {

    Administrator toDomain(AdministratorEntity entity);

    Administrator toDomain(AdministratorCreationRequestResource resource);

    AdministratorEntity toEntity(Administrator administrator);

    AdministratorResource toResource(Administrator administrator);

    List<AdministratorResource> toResource(List<Administrator> administrator);
}
