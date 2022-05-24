package com.oopsiedaisy.flowers.controller.resource;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

import com.oopsiedaisy.flowers.repository.enums.FlowerColorEnum;

@Value
@Builder
public class FlowerResource {

    UUID uuid;

    String title;

    boolean bouquet;

    FlowerColorEnum baseColor;

    BigDecimal price;

    byte[] image;

}
