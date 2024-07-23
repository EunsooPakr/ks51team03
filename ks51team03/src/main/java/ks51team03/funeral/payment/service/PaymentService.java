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

    // 결제 상세 내역 가져오기 위한 업체 코드 조회
    public PaymentDto getPaymentDetail(String fpcode) {

        log.info("fpcode: {}", fpcode);

        return paymentMapper.getPaymentDetail(fpcode);
    }

    //업체별 결제 내역 리스트
    public List<PaymentDto> getPaymentServiceCompnay(PaymentDto paymentDto) {
        log.info("getPaymentServiceCompnay");
        return paymentMapper.getPaymentServiceCompnay(paymentDto);
    }


}
