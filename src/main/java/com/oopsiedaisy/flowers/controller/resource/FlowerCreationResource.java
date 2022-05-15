package com.oopsiedaisy.flowers.controller.resource;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class FlowerCreationResource {

    @NotNull
    String title;

    @NotNull
    Boolean bouquet;

    @NotNull
    String baseColor;

    @NotNull
    BigDecimal price;

    MultipartFile image;

}
