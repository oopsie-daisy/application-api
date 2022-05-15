package com.oopsiedaisy.payments.domain;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
public class ItemsToBuy {

    List<UUID> items;

    PaymentProvider paymentProvider;

    String senderIban;

    BigDecimal amountToPay;
}
