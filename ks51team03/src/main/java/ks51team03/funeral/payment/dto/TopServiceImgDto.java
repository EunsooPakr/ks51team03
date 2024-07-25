package ks51team03.funeral.payment.dto;

import lombok.Data;

@Data
public class TopServiceImgDto {

    private String fpcode; // 결제 테이블 기본키
    private String ccode;  // 업체 코드
    private String fscode; // 결제한 예약 서비스 코드
    private String frcode; // 결제한 예약 서비스 코드
    private String fpName; // 결제한 예약 서비스 명
    private String fpFinal;// 결제한 예약 서비스 가격
    private String fpPayDate; // 결제
    private String fileIdx;
    private String filePath; // 파일 경로
    private String funeralserviceTitle; // 장례 서비스 이름
}
