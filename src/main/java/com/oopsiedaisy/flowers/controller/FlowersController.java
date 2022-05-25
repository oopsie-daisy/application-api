package com.oopsiedaisy.flowers.controller;

import com.oopsiedaisy.config.annotations.JwtValidated;
import com.oopsiedaisy.flowers.controller.resource.FlowerCreationResource;
import com.oopsiedaisy.flowers.controller.resource.FlowerResource;
import com.oopsiedaisy.flowers.controller.util.FlowerFilter;
import com.oopsiedaisy.flowers.mapper.FlowerMapper;
import com.oopsiedaisy.flowers.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flowers")
public class FlowersController {

    private final FlowerService service;

    private final FlowerMapper mapper;

    @GetMapping
    public List<FlowerResource> getAllFlowers(FlowerFilter filter) {
        return mapper.toResource(service.getAllFlowers(filter));
    }

    @JwtValidated
    @PostMapping
    public List<FlowerResource> addFlowers(@RequestBody @Valid List<FlowerCreationResource> flowersToAdd) {
        return mapper.toResource(service.addFlowers(mapper.fromResourceToDomain(flowersToAdd)));
    }

    @GetMapping("/{uuid}")
    public FlowerResource getFlowerByUuid(@PathVariable("uuid") UUID uuid) {
        return mapper.toResource(service.getFlowerByUuid(uuid));
    }
}
