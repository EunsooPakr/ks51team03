package ks51team03.company.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ComReview {
    private String revCode;
    private String memberId;
    private String cCode;
    private String revAdminDate;
    private String revUpdateDate;
    private String revImg;
    private MultipartFile revImgFile;
    private int revScope;
    private String revContent;
    private String companyName;
    private String filePath;
}
