package com.oopsiedaisy.test;

import com.oopsiedaisy.auth.controller.resource.CustomerResource;
import com.oopsiedaisy.config.JwtValidated;
import com.oopsiedaisy.customers.mapper.CustomerMapper;
import com.oopsiedaisy.customers.service.CustomerService;
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

    private final CustomerService service;
    private final CustomerMapper mapper;

    @GetMapping("/{uuid}")
    @JwtValidated
    public CustomerResource getCustomerInfo(@PathVariable String uuid) {
        return mapper.toResource(service.getCustomer(uuid));
    }
}
