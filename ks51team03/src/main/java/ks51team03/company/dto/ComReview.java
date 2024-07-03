package ks51team03.company.dto;

import lombok.Data;

@Data
public class ComReview {
    private String revCode;
    private String memberId;
    private String revCategory;
    private String cCode;
    private String revAdminDate;
    private String revUpdateDate;
    private String revDeleteDate;
    private String revImg;
    private int revScope;
    private String revContent;
}
