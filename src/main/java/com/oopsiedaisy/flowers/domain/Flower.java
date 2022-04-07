package com.oopsiedaisy.flowers.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Flower {

    String title;

    boolean isBouquet;

    String baseColor;

    BigDecimal price;

}
