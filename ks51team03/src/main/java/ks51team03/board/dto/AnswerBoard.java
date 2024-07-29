package ks51team03.board.dto;

import lombok.Data;

@Data // @Data = @Setter + @Getter + @ToString
public class AnswerBoard {
	private String aboardCode;
	private String memberId;
	private String nboardCode;
	private String aboardTitle;
	private String aboardContent;
	private String aboardRegistDate;
	private String aboardUpdateDate;
	private String aboardDeleteDate;
	private int aboardSelected;			
}
