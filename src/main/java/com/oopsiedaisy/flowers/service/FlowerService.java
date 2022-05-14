package com.oopsiedaisy.flowers.service;

import com.oopsiedaisy.config.annotations.LogAudit;
import com.oopsiedaisy.flowers.controller.util.FlowerFilter;
import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.flowers.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlowerService {

    private final FlowerRepository repository;

    @LogAudit
    public List<Flower> getAllFlowers(FlowerFilter filter) {
        return repository.getAll(filter);
    }
}
