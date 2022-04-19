package com.oopsiedaisy.flowers.service;

import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.flowers.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerService {

    private final FlowerRepository repository;

    public List<Flower> getAllFlowers() {
        return repository.getAll();
    }
}
