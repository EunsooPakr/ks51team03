package ks51team03.company.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CompanyImg {
    private String ciCode;
    private String cCode;
    private MultipartFile revImgFile;
    private String fileIdx;
    private String filePath;
}
