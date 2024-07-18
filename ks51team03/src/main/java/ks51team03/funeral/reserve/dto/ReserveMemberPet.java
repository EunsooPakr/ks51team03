package ks51team03.funeral.reserve.dto;

import lombok.Data;

@Data
public class ReserveMemberPet {

    private String memberId;
    private String memberName;
    private String memberLevel;
    private String memberLevelName;
    private String memberPhone;
    private String memberEmail;
    private String petCode;
    private String petName;
    private String petWeight;

}
