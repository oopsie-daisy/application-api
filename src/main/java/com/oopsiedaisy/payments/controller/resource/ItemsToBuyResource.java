package com.oopsiedaisy.payments.controller.resource;

import com.oopsiedaisy.payments.domain.PaymentProvider;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
public class ItemsToBuyResource {

    @NotEmpty
    List<UUID> items;

    @NotNull
    PaymentProvider paymentProvider;

    @NotNull
    String senderIban;

    @NotNull
    BigDecimal amountToPay;

}
