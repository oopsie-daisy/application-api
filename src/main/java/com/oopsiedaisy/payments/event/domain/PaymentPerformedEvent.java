package com.oopsiedaisy.payments.event.domain;

import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.payments.domain.Payment;
import lombok.Value;

import java.util.List;

@Value
public class PaymentPerformedEvent {

    Payment payment;

    List<Flower> flowersToBeSold;
}
