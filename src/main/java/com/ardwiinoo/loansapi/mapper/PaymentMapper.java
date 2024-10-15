package com.ardwiinoo.loansapi.mapper;

import com.ardwiinoo.loansapi.model.dto.payment.PaymentDto;
import com.ardwiinoo.loansapi.model.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {

    PaymentMapper MAPPER = Mappers.getMapper(PaymentMapper.class);

    PaymentDto toPaymentDto(Payment payment);
}
