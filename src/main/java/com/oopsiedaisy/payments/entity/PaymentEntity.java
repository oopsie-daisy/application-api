package com.oopsiedaisy.payments.entity;

import com.oopsiedaisy.payments.domain.PaymentProvider;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
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
@Table(name = "payment")
@FieldDefaults(level = PRIVATE)
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    Integer id;

    @Column(nullable = false, updatable = false)
    @Type(type="uuid-char")
    UUID uuid;

    @Column(nullable = false)
    PaymentProvider paymentProvider;

    @Column(nullable = false)
    @PositiveOrZero
    BigDecimal amountToPay;

    @Column(nullable = false)
    String senderIban;

    @Version
    @Column(name = "OPT_LOCK_VERSION", columnDefinition = "integer default 0")
    Integer version;

    @PrePersist
    private void setUuid() {
        if (this.uuid == null) {
            uuid = randomUUID();
        }
    }

}
