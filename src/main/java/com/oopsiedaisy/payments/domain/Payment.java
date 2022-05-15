package com.oopsiedaisy.payments.domain;

import com.oopsiedaisy.payments.controller.resource.PaymentStatus;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class Payment {

    UUID uuid;

    PaymentProvider paymentProvider;

    BigDecimal amountToPay;

    String senderIban;

    PaymentStatus status;

}
