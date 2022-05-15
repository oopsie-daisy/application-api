package com.oopsiedaisy.test;

import com.oopsiedaisy.auth.controller.resource.AdministratorResource;
import com.oopsiedaisy.config.annotations.JwtValidated;
import com.oopsiedaisy.customers.mapper.AdministratorMapper;
import com.oopsiedaisy.customers.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test purpose rest controller to check {@link JwtValidated} validity. To delete afterwards.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final AdministratorService service;
    private final AdministratorMapper mapper;

    @GetMapping("/{uuid}")
    @JwtValidated
    public AdministratorResource getCustomerInfo(@PathVariable String uuid) {
        return mapper.toResource(service.getCustomer(uuid));
    }
}
