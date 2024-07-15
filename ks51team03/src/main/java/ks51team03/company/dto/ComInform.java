package ks51team03.company.dto;

import lombok.Data;

import java.util.List;

@Data
public class ComInform {
    private String informCode;
    private String memberId;
    private String informDateTime;
    private String informCycle;
    private String informValue;
    private String informContents;
    private List<String> memberIds;
}
