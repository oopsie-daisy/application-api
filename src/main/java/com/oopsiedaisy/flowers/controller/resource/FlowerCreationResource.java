package com.oopsiedaisy.flowers.controller.resource;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import com.oopsiedaisy.flowers.repository.enums.FlowerColorEnum;

@Value
public class FlowerCreationResource {

    @NotNull
    String title;

    @NotNull
    Boolean bouquet;

    @NotNull
    FlowerColorEnum baseColor;

    @NotNull
    BigDecimal price;

    MultipartFile image;

}
