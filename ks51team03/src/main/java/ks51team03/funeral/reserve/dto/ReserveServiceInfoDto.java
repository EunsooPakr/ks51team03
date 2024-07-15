package ks51team03.funeral.reserve.dto;

import lombok.Data;

@Data
public class ReserveServiceInfoDto {

    private String reserveId; // 서비스가격
    private String reserveCompanyCode; // 업체코드
    private String reserveCode; //
    private String reserveServiceCode; // 장례 서비스 이름
    private String reserveStartDate; // 예약일
    private String reserveEndDate; //예약 종료일
    private String funeralserviceTitle;
    private String funeralservicePrice; //장례 서비스 가격
    private String companyName;
}
