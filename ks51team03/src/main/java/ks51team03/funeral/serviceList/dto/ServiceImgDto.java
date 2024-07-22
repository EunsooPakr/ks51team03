package ks51team03.funeral.serviceList.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ServiceImgDto {

    private String fsicode;
    private String ccode; // 업체코드
    private String fscode;
    private String funeralserviceId; // 등록자 아이디
    private String fileIdx;
    private String fileCate;
    private MultipartFile furImgFile;
    private String fileOriginName; // 파일 원본 이름
    private String fileNewName; // 파일 새로운 이름
    private String filePath; // 파일 경로
    private Long fileSize; // 파일 크기
    private String fileRegDate; // 파일 등록 날짜
}
