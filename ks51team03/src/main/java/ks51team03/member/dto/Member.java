package ks51team03.member.dto;
import lombok.Data;

@Data // @Data = @Setter + @Getter + @ToString
public class Member {
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberLevel;
    private String memberLevelName;
    private String memberGrade;
    private String memberGender;
    private String memberBirth;
    private String memberAddr;
    private String memberAddrDetail;
    private String memberPostNum;
    private String memberPhone;
    private String memberEmail;
    private int memberPet;
    private int memberSmsCheck;
    private int  memberEmailCheck; 
    private String memberRgstDate;  

}
