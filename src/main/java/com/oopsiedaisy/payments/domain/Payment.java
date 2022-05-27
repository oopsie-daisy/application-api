package com.oopsiedaisy.payments.domain;

import com.oopsiedaisy.payments.controller.resource.PaymentStatus;
import com.oopsiedaisy.payments.repository.enums.DeliveryOptionEnum;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class Payment {

    String customerName;

    String customerEmail;

    String customerAddress;

    UUID uuid;

    PaymentProvider paymentProvider;

    DeliveryOptionEnum deliveryOption;

    BigDecimal amountToPay;

    String senderIban;

    PaymentStatus status;

    UUID item;

    Integer quantity;

}
