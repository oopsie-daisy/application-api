package com.oopsiedaisy.payments.controller.resource;

import com.oopsiedaisy.payments.domain.PaymentProvider;
import com.oopsiedaisy.payments.repository.enums.DeliveryOptionEnum;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class ItemsToBuyResource {

    @NotNull
    String customerName;

    @Email
    @NotNull
    String customerEmail;

    @NotNull
    String customerAddress;

    @NotNull
    UUID item;

    @NotNull
    Integer quantity;

    @NotNull
    PaymentProvider paymentProvider;

    @NotNull
    DeliveryOptionEnum deliveryOption;

    @NotNull
    String senderIban;

    @NotNull
    BigDecimal amountToPay;

}
