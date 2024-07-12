package ks51team03.funeral.payment.service;

import ks51team03.funeral.payment.dto.PaymentDto;
import ks51team03.funeral.payment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentService {

    private final PaymentMapper paymentMapper;

    //장례 결제 확인
    public List<PaymentDto> confirmPayment(PaymentDto paymentDto){

        return paymentMapper.confirmPayment(paymentDto);
    }
}
