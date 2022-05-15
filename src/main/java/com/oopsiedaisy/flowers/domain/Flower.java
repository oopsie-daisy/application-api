package com.oopsiedaisy.flowers.domain;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class Flower {

    UUID uuid;

    String title;

    boolean bouquet;

    String baseColor;

    BigDecimal price;

    byte[] image;

}
