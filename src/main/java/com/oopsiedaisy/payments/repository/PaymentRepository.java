package com.oopsiedaisy.payments.repository;

import com.oopsiedaisy.payments.domain.Payment;
import com.oopsiedaisy.payments.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final PaymentMapper mapper;

    private final PaymentJpaRepository repository;

    public Payment save(Payment payment) {
        return mapper.toDomain(repository.save(mapper.toEntity(payment)));
    }

    public Payment findBySenderIban(String iban) {
        return mapper.toDomain(repository.findBySenderIban(iban));
    }
}
