package com.oopsiedaisy.customers.mapper;

import com.oopsiedaisy.auth.controller.resource.CustomerCreationRequestResource;
import com.oopsiedaisy.auth.controller.resource.CustomerResource;
import com.oopsiedaisy.config.MapStructConfig;
import com.oopsiedaisy.customers.domain.Customer;
import com.oopsiedaisy.customers.repository.entity.CustomerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class, uses = AddressMapper.class)
public interface CustomerMapper {

    Customer toDomain(CustomerEntity entity);

    Customer toDomain(CustomerCreationRequestResource resource);

    CustomerEntity toEntity(Customer customer);

    CustomerResource toResource(Customer customer);

    List<CustomerResource> toResource(List<Customer> customer);
}
