package com.oopsiedaisy.payments.entity;

import com.oopsiedaisy.payments.domain.PaymentProvider;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@FieldDefaults(level = PRIVATE)
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    Integer id;

    @Column(nullable = false, updatable = false)
    UUID uuid = randomUUID();

    @Column(nullable = false)
    PaymentProvider paymentProvider;

    @Column(nullable = false)
    BigDecimal amountToPay;

    @Column(nullable = false)
    String senderIban;

}
