package com.oopsiedaisy.flowers.mapper;

import com.oopsiedaisy.config.MapStructConfig;
import com.oopsiedaisy.flowers.controller.resource.FlowerCreationResource;
import com.oopsiedaisy.flowers.controller.resource.FlowerResource;
import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.flowers.repository.entity.FlowerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Mapper(config = MapStructConfig.class)
public interface FlowerMapper {

    FlowerResource toResource(Flower flower);

    List<FlowerResource> toResource(List<Flower> flowers);

    Flower toDomain(FlowerEntity flower);

    List<Flower> toDomain(List<FlowerEntity> flowers);

    Set<Flower> toDomain(Set<FlowerEntity> flowers);

    @Mapping(target = "image", source = "image.bytes")
    Flower fromResourceToDomain(FlowerCreationResource flower) throws IOException;

    List<Flower> fromResourceToDomain(List<FlowerCreationResource> flowers);

    List<FlowerEntity> toEntity(List<Flower> flowers);

    FlowerEntity toEntity(Flower flower);
}
