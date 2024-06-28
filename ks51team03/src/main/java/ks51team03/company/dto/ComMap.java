package ks51team03.company.dto;

import ks51team03.member.dto.Member;
import lombok.Data;

@Data
public class ComMap {
    private final String cCode;
    private final String cmLink;
    private final String cmX;
    private final String cmY;
    private String memberId;
}
