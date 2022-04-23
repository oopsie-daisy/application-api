package com.oopsiedaisy.customers.mapper;

import com.oopsiedaisy.config.MapStructConfig;
import com.oopsiedaisy.customers.domain.Address;
import com.oopsiedaisy.customers.repository.entity.AddressEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface AddressMapper {

    Address toDomain(AddressEntity entity);

    List<Address> toDomain(List<AddressEntity> entities);

}
