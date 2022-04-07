package com.oopsiedaisy.payments.domain;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Payment {

    PaymentProvider paymentProvider;

    BigDecimal amountToPay;

    String senderIban;

}
