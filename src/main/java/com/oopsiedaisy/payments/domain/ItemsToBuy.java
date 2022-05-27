package com.oopsiedaisy.payments.domain;

import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

import com.oopsiedaisy.payments.repository.enums.DeliveryOptionEnum;

@Value
public class ItemsToBuy {

    String customerName;

    String customerEmail;

    String customerAddress;

    UUID item;

    Integer quantity;

    PaymentProvider paymentProvider;

    DeliveryOptionEnum deliveryOption;

    String senderIban;

    BigDecimal amountToPay;
}
