package ks51team03.board.dto;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data // @Data = @Setter + @Getter + @ToString
public class NoticeBoard {
	private String nboardCode;
	private String memberId;
	private String boardCode;
	private String boardCateCode;
	private String nboardTitle;
	private String nboardContent;
	private String nboardRegistDate;
	private String nboardUpdateDate;
	private String nboardDeleteDate;
	private String nboardImg;
	private int nboardView;				//조회수
	private int nboardRec;				//추천수
	private MultipartFile nboardImgFile;
}
