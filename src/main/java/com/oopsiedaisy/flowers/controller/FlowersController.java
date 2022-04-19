package com.oopsiedaisy.flowers.controller;

import com.oopsiedaisy.flowers.controller.resource.FlowerResource;
import com.oopsiedaisy.flowers.mapper.FlowerMapper;
import com.oopsiedaisy.flowers.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flowers")
public class FlowersController {

    private final FlowerService service;

    private final FlowerMapper mapper;

    @GetMapping
    public List<FlowerResource> getAllFlowers() {
        return mapper.toResource(service.getAllFlowers());
    }
}
