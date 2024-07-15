package ks51team03.funeral.reserve.dto;

import lombok.Data;

@Data
public class ReservePaymentDto {

    private String fpcode; // 결제 테이블 기본키
    private String txId; // 트랜잭션 아이디
    private String code; // 오류 코드
    private String message; // 오류 문구
    private String ccode;  // 업체 코드
    private String reserveId;     // 결제한 유저 ID
    private String frcode; // 결제한 예약 서비스 코드
    private String fpName; // 결제한 예약 서비스 명
    private String fpFinal;// 결제한 예약 서비스 가격
    private String fpPayDate; // 결제 시간
    private String fpMethod;  // 결제 수단
    private String fpStatus;  // 결제 상태

}
