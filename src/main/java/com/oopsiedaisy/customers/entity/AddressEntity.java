package com.oopsiedaisy.customers.entity;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Data
@Entity
@FieldDefaults(level = PRIVATE)
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    Integer id;

    @Column(nullable = false, updatable = false)
    UUID uuid = randomUUID();

    @Column(nullable = false)
    String country;

    @Column(nullable = false)
    String city;

    @Column(nullable = false)
    String streetName;

    @Column(nullable = false)
    String houseNumber;

    @Column(nullable = false)
    String apartmentNumber;

    @Column(nullable = false)
    String zipCode;

}
