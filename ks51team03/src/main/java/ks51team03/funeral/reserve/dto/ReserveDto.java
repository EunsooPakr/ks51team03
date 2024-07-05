package ks51team03.funeral.reserve.dto;

import lombok.Data;

public class ReserveDto {


    @Data
    public class Funeral_reserve{

        private String reserveCode;
        private String reserveCompanyCode; // 업체코드
        private String reserveServiceCode; // 장례 서비스 이름
        private String reserveId; // 서비스가격
        private String reserveStartDate; // 예약일
        private String reserveEndDate; //예약 종료일
        private String reservePhone; // 주문자 연락처
        private String reservePayment; // 결제 상태
        private String reserveConfirm; // 예약 확인
        private String reserveRegDate;  //예약 등록일
        private int reserveTime; //예약 시간
    }
}
