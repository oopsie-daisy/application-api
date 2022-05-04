package com.oopsiedaisy.payments.controller;


import com.oopsiedaisy.payments.domain.PaymentProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @GetMapping(value = "/payment-provider")

    public List<PaymentProvider> getPaymentProvider() {
        return asList(PaymentProvider.values());
    }
}
