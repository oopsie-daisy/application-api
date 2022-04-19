package com.oopsiedaisy.flowers.mapper;

import com.oopsiedaisy.config.MapStructConfig;
import com.oopsiedaisy.flowers.controller.resource.FlowerResource;
import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.flowers.repository.entity.FlowerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface FlowerMapper {

    FlowerResource toResource(Flower flower);

    List<FlowerResource> toResource(List<Flower> flowers);

    Flower toDomain(FlowerEntity flower);

    List<Flower> toDomain(List<FlowerEntity> flowers);
}
