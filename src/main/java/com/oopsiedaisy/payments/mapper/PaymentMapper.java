package com.oopsiedaisy.payments.mapper;

import com.oopsiedaisy.config.MapStructConfig;
import com.oopsiedaisy.payments.controller.resource.ItemsToBuyResource;
import com.oopsiedaisy.payments.domain.ItemsToBuy;
import com.oopsiedaisy.payments.domain.Payment;
import com.oopsiedaisy.payments.repository.entity.PaymentEntity;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface PaymentMapper {

    ItemsToBuy toDomain(ItemsToBuyResource resource);

    Payment toDomain(PaymentEntity entity);

    PaymentEntity toEntity(Payment payment);
}
