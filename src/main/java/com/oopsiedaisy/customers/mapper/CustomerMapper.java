package com.oopsiedaisy.customers.mapper;

import com.oopsiedaisy.config.MapStructConfig;
import com.oopsiedaisy.customers.domain.Customer;
import com.oopsiedaisy.customers.repository.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface CustomerMapper {

    Customer toDomain(CustomerEntity entity);
}
