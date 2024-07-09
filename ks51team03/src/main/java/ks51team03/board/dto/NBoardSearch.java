package ks51team03.board.dto;
import lombok.Data;

@Data // @Data = @Setter + @Getter + @ToString
public class NBoardSearch {
	private String searchKey;
	private String searchValue;
	private String searchText;
}
