package ks51team03.files.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileRequest {
    private String fileIdx;
    private String fileOriginName;
    private String fileNewName;
    private String filePath;
    private long fileSize;
    private String fileRegDate;
    private String fileCate;
}
