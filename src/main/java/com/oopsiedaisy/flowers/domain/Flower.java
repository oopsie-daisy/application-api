package com.oopsiedaisy.flowers.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

import com.oopsiedaisy.flowers.repository.enums.FlowerColorEnum;

@Value
@Builder
public class Flower {

    UUID uuid;

    String title;

    boolean bouquet;

    FlowerColorEnum baseColor;

    BigDecimal price;

    byte[] image;

}
