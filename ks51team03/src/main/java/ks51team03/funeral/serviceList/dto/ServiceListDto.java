package ks51team03.funeral.serviceList.dto;

import lombok.Data;

@Data
public class ServiceListDto {

    private String funeralserviceCode;
    private String funeralserviceCcode; // 업체코드
    private String funeralserviceTitle; // 장례 서비스 이름
    private int funeralservicePrice; // 서비스가격
    private float funeralserviceWeight; // 허용무게(kg)
    private String funeralserviceId; // 등록자 아이디
    private String funeralserviceRegDate; // 등록일
    private String funeralserviceState; //상태
}
