package ks51team03.files.mapper;

import ks51team03.files.dto.FileRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    // 리뷰 이미지 삭제 쿼리
    void deleteReview(String fileIdx);

    // 리뷰 이미지 삭제 전 fileIdx컬럼 null로 변환
    void updateReviewImg(String revCode);

    // 로컬 파일 삭제를 위한 업채 대표 이미지 기본키 조회
    FileRequest getFileByFileIdx(String imageId);

    // 로컬 파일 삭제를 위한 리뷰 기본키 조회
    FileRequest getFileByRevCode(String review);

    // 파일 등록
    void addFile(FileRequest fileRequest);
}
