package ks51team03.funeral.payment.dto;

import lombok.Data;

@Data
public class PaymentDto {

    private String fpcode; // 결제 테이블 기본키
    private String ccode;  // 업체 코드
    private String fscode; // 결제한 예약 서비스 코드
    private String frcode; // 결제한 예약 서비스 코드
    private String reserveId;     // 결제한 유저 ID
    private String fpName; // 결제한 예약 서비스 명
    private String fpFinal;// 결제한 예약 서비스 가격
    private String fpPayDate; // 결제일
    private String fpMethod;  // 결제 수단
    private String fpStatus;  // 결제 상태
    private String comName; // 장례 서비스 타이틀 명
    private String petName; // 애완동물 이름
    private String reserveRegDate;  //예약 등록일
    private String name;  //예약 등록일
    private String reserveStartDate;

}
