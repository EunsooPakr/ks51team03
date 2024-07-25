package ks51team03.board.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NBoardImg {
	private String nbiCode;
    private String nbCode;
    private MultipartFile nBoardImgFile;
    private String fileIdx;
    private String filePath;
}
