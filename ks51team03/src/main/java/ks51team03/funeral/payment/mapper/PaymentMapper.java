package ks51team03.funeral.payment.mapper;

import ks51team03.funeral.payment.dto.PaymentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    List<PaymentDto> confirmPayment(PaymentDto paymentDto);

    //결제 상세 내역 가지고 오기 위한 업체 코드 조회
    PaymentDto getPaymentDetail(String fpcode);  // 수정된 부분

    //업체별 결제 내역 가져오기
    List<PaymentDto> getPaymentServiceCompnay(PaymentDto paymentDto);
}
