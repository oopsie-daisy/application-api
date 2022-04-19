package com.oopsiedaisy.flowers.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
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
@Table(name = "flower")
@FieldDefaults(level = PRIVATE)
public class FlowerEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    Integer id;

    @Column(nullable = false, updatable = false)
    @Type(type="uuid-char")
    UUID uuid;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    boolean bouquet;

    @Column(nullable = false)
    String baseColor;

    @DecimalMin("0.01")
    @Column(nullable = false)
    BigDecimal price;

    @PrePersist
    private void setUuid() {
        if (this.uuid != null) {
            uuid = randomUUID();
        }
    }

}