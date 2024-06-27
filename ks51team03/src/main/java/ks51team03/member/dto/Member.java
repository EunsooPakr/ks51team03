package ks51team03.member.dto;

import java.util.List;

import lombok.Data;

@Data // @Data = @Setter + @Getter + @ToString
public class Member {
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberLevel;
	private String memberLevelName;
	private String memberEmail;
	private String memberAddr;
	private String memberRegDate;
}
