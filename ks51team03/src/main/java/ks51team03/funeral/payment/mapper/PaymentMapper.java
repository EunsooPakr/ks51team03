package ks51team03.funeral.payment.mapper;

import ks51team03.funeral.payment.dto.PaymentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    List<PaymentDto> confirmPayment(PaymentDto paymentDto);
}
