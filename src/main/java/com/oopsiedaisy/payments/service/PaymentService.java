package com.oopsiedaisy.payments.service;

import com.oopsiedaisy.config.exceptions.FailedPaymentException;
import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.flowers.repository.FlowerRepository;
import com.oopsiedaisy.payments.controller.resource.PaymentStatus;
import com.oopsiedaisy.payments.domain.ItemsToBuy;
import com.oopsiedaisy.payments.domain.Payment;
import com.oopsiedaisy.payments.domain.PaymentProvider;
import com.oopsiedaisy.payments.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.oopsiedaisy.payments.controller.resource.PaymentStatus.COMPLETED;
import static com.oopsiedaisy.payments.controller.resource.PaymentStatus.FAILED;
import static java.util.UUID.randomUUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private static final String NO_ITEMS_AVAILABLE = "Item(s) you are trying to buy do not exist";

    private final FlowerRepository repository;

    private final PaymentRepository paymentRepository;

    public PaymentStatus completePayment(ItemsToBuy itemsToBuy) {
        List<Flower> flowersToBeSold = repository.getByUuids(itemsToBuy.getItems());
        if (flowersToBeSold.size() != itemsToBuy.getItems().size()) {
            savePaymentInfo(itemsToBuy.getPaymentProvider(), itemsToBuy.getAmountToPay(), itemsToBuy.getSenderIban(), FAILED);
            throw new FailedPaymentException(NO_ITEMS_AVAILABLE);
        }
        try {
            PaymentStatus status = repository.removeAll(itemsToBuy.getItems());
            savePaymentInfo(itemsToBuy.getPaymentProvider(), itemsToBuy.getAmountToPay(), itemsToBuy.getSenderIban(), status);
        } catch (FailedPaymentException e) {
            savePaymentInfo(itemsToBuy.getPaymentProvider(), itemsToBuy.getAmountToPay(), itemsToBuy.getSenderIban(), FAILED);
            throw e;
        }
        return COMPLETED;
    }

    private void savePaymentInfo(PaymentProvider paymentProvider, BigDecimal amountToPay, String senderIban, PaymentStatus status) {
        paymentRepository.save(new Payment(randomUUID(), paymentProvider, amountToPay, senderIban, status));
    }
}
