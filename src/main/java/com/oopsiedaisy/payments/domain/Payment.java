package com.oopsiedaisy.payments.domain;

import com.oopsiedaisy.payments.controller.resource.PaymentStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class Payment {

    String customerName;

    String customerEmail;

    String customerAddress;

    UUID uuid;

    PaymentProvider paymentProvider;

    BigDecimal amountToPay;

    String senderIban;

    PaymentStatus status;

    List<UUID> items;

}
