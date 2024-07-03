package ks51team03.company.dto;

import ks51team03.member.dto.Member;
import lombok.Data;

@Data
public class Company {
    private String companyCode;
    private String companyClass;
    private String companyName;
    private String companyCeo;
    private String companyAddr;
    private String companyAddrDetail;
    private String companyPhone;
    private int companyStfCount;
    private String companyEmail;
    private boolean companyParking;
    private String companyPage;
    private String companyRegDate;
    private String memberId;
    private String comPostNum;

}
