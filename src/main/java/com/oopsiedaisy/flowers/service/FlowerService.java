package com.oopsiedaisy.flowers.service;

import com.oopsiedaisy.flowers.controller.util.FlowerFilter;
import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.flowers.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlowerService {

    private final FlowerRepository repository;

    public List<Flower> getAllFlowers(FlowerFilter filter) {
        return repository.getAll(filter);
    }

    public List<Flower> addFlowers(List<Flower> flowersToAdd) {
        return repository.addAll(flowersToAdd);
    }

    public Flower getFlowerByUuid(UUID uuid) {
        return repository.getByUuid(uuid);
    }
}
