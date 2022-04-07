package com.oopsiedaisy.flowers.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class Flower {

    UUID uuid;

    String title;

    boolean isBouquet;

    String baseColor;

    BigDecimal price;

}
