package ks51team03.board.dto;
import lombok.Data;

@Data // @Data = @Setter + @Getter + @ToString
public class BoardCategory {
	private String boardCateCode;
	private String boardCode;
	private String boardCateName;
	private String boardCateValue;
	private String boardInfo;
	private String memberId;
	private String boardCateRegistDate;
	private int boardCateState;
}
