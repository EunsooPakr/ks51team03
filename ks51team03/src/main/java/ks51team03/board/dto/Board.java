package ks51team03.board.dto;
import lombok.Data;

@Data // @Data = @Setter + @Getter + @ToString
public class Board {
	private String boardCode;
	
	private String boardName;
	private int boardKindNum;
	private String memberId;
	private String boardRegistDate;
	private int boardState;

}
