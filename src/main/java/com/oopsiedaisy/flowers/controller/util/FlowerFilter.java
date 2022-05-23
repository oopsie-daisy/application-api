package com.oopsiedaisy.flowers.controller.util;

import lombok.Value;

import java.math.BigDecimal;

import com.oopsiedaisy.flowers.repository.enums.FlowerColorEnum;

@Value
public class FlowerFilter {

    String title;

    Boolean bouquet;

    FlowerColorEnum color;

    BigDecimal priceFrom;

    BigDecimal priceTo;

}
