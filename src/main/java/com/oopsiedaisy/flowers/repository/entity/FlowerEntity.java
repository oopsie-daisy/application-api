package com.oopsiedaisy.flowers.repository.entity;

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

import com.oopsiedaisy.flowers.repository.enums.FlowerColorEnum;

@Getter
@Entity
@Builder
@NoArgsConstructor
@EqualsAndHashCode(of = "uuid")
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    FlowerColorEnum baseColor;

    @PositiveOrZero
    @Column(nullable = false)
    BigDecimal price;

    @Lob
    @Column
    byte[] image;

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
