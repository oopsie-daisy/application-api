package com.oopsiedaisy.flowers.entity;

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
public class FlowerEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    Integer id;

    @Column(nullable = false, updatable = false)
    UUID uuid = randomUUID();

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    boolean isBouquet;

    @Column(nullable = false)
    String baseColor;

    @Column(nullable = false)
    BigDecimal price;

}
