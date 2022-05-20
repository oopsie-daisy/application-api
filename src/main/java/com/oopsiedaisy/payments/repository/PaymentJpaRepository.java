package com.oopsiedaisy.payments.repository;

import com.oopsiedaisy.payments.repository.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Integer> {

    PaymentEntity findBySenderIban(String iban);
}
