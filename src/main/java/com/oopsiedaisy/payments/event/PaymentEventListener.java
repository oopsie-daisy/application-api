package com.oopsiedaisy.payments.event;

import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.mail.EmailService;
import com.oopsiedaisy.payments.event.domain.PaymentPerformedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private static final String SUBJECT = "Oopsie Daisy užsakymo patvirtinimas";
    private static final String ITEMS_PARAM = "items";
    private static final String CUSTOMER_ADDRESS_PARAM = "customerAddress";
    private static final String EMAIL_TEMPLATE = "order-success.html";

    private final EmailService emailService;

    @Async
    @EventListener
    public void handlePaymentEvent(PaymentPerformedEvent event) {
        List<Flower> boughtFlowers = event.getFlowersToBeSold();
        Map<String, Object> params = new HashMap<>();
        params.put(CUSTOMER_ADDRESS_PARAM, event.getPayment().getCustomerAddress());
        params.put(ITEMS_PARAM, boughtFlowers);
        emailService.sendSimpleMessage(event.getPayment().getCustomerEmail(), SUBJECT, EMAIL_TEMPLATE, params);
    }
}
