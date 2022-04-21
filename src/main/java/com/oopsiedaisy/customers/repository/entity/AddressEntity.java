package com.oopsiedaisy.customers.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
@Table(name = "address")
@FieldDefaults(level = PRIVATE)
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    Integer id;

    @Column(nullable = false, updatable = false)
    @Type(type="uuid-char")
    UUID uuid;

    @Column(nullable = false)
    String country;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String streetName;

    @Column(nullable = false)
    String houseNumber;

    @Column
    String apartmentNumber;

    @Column(nullable = false)
    String zipCode;

    @Setter
    @ManyToOne
    @JoinColumn(name = "customer_id")
    CustomerEntity customer;

    @PrePersist
    private void setUuid() {
        if (this.uuid == null) {
            uuid = randomUUID();
        }
    }

}
