package com.oopsiedaisy.flowers.controller.util;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class FlowerFilter {

    String title;

    Boolean bouquet;

    String color;

    BigDecimal priceFrom;

    BigDecimal priceTo;

}
