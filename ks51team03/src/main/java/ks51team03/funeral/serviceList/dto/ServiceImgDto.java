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
}
