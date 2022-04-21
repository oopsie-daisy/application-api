package com.oopsiedaisy.customers.repository.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
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
@Table(name = "customer")
@FieldDefaults(level = PRIVATE)
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    Integer id;

    @Column(nullable = false, updatable = false)
    @Type(type="uuid-char")
    UUID uuid;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String fullName;

    @OneToMany(mappedBy = "customer")
    List<AddressEntity> addresses;

    @Column(nullable = false)
    String phoneNumber;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    @PrePersist
    private void setUuid() {
        if (this.uuid == null) {
            uuid = randomUUID();
        }
    }
}
