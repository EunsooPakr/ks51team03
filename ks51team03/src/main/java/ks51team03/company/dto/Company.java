package ks51team03.company.dto;

import ks51team03.member.dto.Member;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    private String comMapX;
    private String comMapY;
    private String cmCode;


}
