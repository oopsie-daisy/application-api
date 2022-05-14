package com.oopsiedaisy.flowers.controller.resource;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class FlowerResource {

    UUID uuid;

    String title;

    boolean bouquet;

    String baseColor;

    BigDecimal price;

    byte[] image;

}
