package ks51team03.files.mapper;

import ks51team03.files.dto.FileRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    // 파일 삭제
    FileRequest getFileByRevCode(String review);

    // 파일 등록
    void addFile(FileRequest fileRequest);
}
