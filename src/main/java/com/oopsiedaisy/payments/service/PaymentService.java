package com.oopsiedaisy.payments.service;

import com.oopsiedaisy.config.exceptions.FailedPaymentException;
import com.oopsiedaisy.flowers.domain.Flower;
import com.oopsiedaisy.flowers.repository.FlowerRepository;
import com.oopsiedaisy.payments.controller.resource.PaymentStatus;
import com.oopsiedaisy.payments.domain.ItemsToBuy;
import com.oopsiedaisy.payments.domain.Payment;
import com.oopsiedaisy.payments.event.domain.PaymentPerformedEvent;
import com.oopsiedaisy.payments.domain.PaymentProvider;
import com.oopsiedaisy.payments.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    private final ApplicationEventPublisher eventPublisher;

    public PaymentStatus completePayment(ItemsToBuy itemsToBuy) {
        List<Flower> flowersToBeSold = checkIfItemsExist(itemsToBuy);
        Payment performedPayment = doPaymentInternal(itemsToBuy);
        eventPublisher.publishEvent(new PaymentPerformedEvent(performedPayment, flowersToBeSold));
        return COMPLETED;
    }

    private Payment doPaymentInternal(ItemsToBuy itemsToBuy) {
        try {
            PaymentStatus status = repository.removeAll(itemsToBuy.getItems());
            return savePaymentInfo(itemsToBuy, status);
        } catch (FailedPaymentException e) {
            savePaymentInfo(itemsToBuy, FAILED);
            throw e;
        }
    }

    private List<Flower> checkIfItemsExist(ItemsToBuy itemsToBuy) {
        List<Flower> flowersToBeSold = repository.getByUuids(itemsToBuy.getItems());
        if (flowersToBeSold.size() != itemsToBuy.getItems().size()) {
            savePaymentInfo(itemsToBuy, FAILED);
            throw new FailedPaymentException(NO_ITEMS_AVAILABLE);
        }
        return flowersToBeSold;
    }

    private Payment savePaymentInfo(ItemsToBuy payment, PaymentStatus status) {
        Payment paymentToSave = Payment.builder()
                .uuid(randomUUID())
                .customerAddress(payment.getCustomerAddress())
                .customerEmail(payment.getCustomerEmail())
                .customerName(payment.getCustomerName())
                .amountToPay(payment.getAmountToPay())
                .paymentProvider(payment.getPaymentProvider())
                .senderIban(payment.getSenderIban())
                .items(payment.getItems())
                .status(status)
                .build();
        return paymentRepository.save(paymentToSave);
    }
}
