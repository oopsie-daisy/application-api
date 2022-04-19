package com.oopsiedaisy.payments.entity;

import com.oopsiedaisy.payments.domain.PaymentProvider;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Entity
@Builder
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    Integer id;

    @Column(nullable = false, updatable = false)
    UUID uuid;

    @Column(nullable = false)
    PaymentProvider paymentProvider;

    @Column(nullable = false)
    BigDecimal amountToPay;

    @Column(nullable = false)
    String senderIban;

    @PrePersist
    private void setUuid() {
        if (this.uuid != null) {
            uuid = randomUUID();
        }
    }

}
