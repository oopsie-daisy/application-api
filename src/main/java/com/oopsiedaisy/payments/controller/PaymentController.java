package com.oopsiedaisy.payments.controller;


import com.oopsiedaisy.payments.controller.resource.ItemsToBuyResource;
import com.oopsiedaisy.payments.controller.resource.PaymentStatus;
import com.oopsiedaisy.payments.domain.PaymentProvider;
import com.oopsiedaisy.payments.mapper.PaymentMapper;
import com.oopsiedaisy.payments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    private final PaymentMapper mapper;

    @GetMapping(value = "/payment-provider")
    public List<PaymentProvider> getPaymentProvider() {
        return asList(PaymentProvider.values());
    }

    @PostMapping("/complete")
    public PaymentStatus completePayment(@RequestBody @Valid ItemsToBuyResource itemsToBuy) {
        return service.completePayment(mapper.toDomain(itemsToBuy));
    }
}
