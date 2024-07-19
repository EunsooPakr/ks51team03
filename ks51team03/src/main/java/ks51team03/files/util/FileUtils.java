package ks51team03.files.util;

import ks51team03.files.dto.FileRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils {
    private final String uploadPath = "/home/ks51team03/attachment";

    public void deleteFile(FileRequest fileRequest) {
        Path filePath = Paths.get("/home/ks51team03" + fileRequest.getFilePath());

        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    /**
     * 다중 파일 업로드
     * @param multipartFiles - 파일 객체 List
     * @return DB에 저장할 파일 정보 List
     */
    public List<FileRequest> uploadFiles(final List<MultipartFile> multipartFiles, String cate) {
        List<FileRequest> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(uploadFile(multipartFile, cate));
        }
        return files;
    }

    /**
     * 단일 파일 업로드
     * @param multipartFile - 파일 객체
     * @return DB에 저장할 파일 정보
     */
    public FileRequest uploadFile(final MultipartFile multipartFile, String cate) {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String fileIdx = "file_"+ UUID.randomUUID().toString().replaceAll("-", "");
        String fileNewName = generateSaveFilename(multipartFile.getOriginalFilename());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")).toString();
        String fileRegDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
        String cateDirPath = makeCateDirectories(uploadPath + File.separator + cate); // 리뷰까지 경로
        String uploadPath =  getUploadPath(cateDirPath,today) + File.separator + fileNewName;


        // 파일의 업로드 경로 설정
        byte[] bytes;
        Path uploadPaths = Paths.get(uploadPath);
        try {
            bytes = multipartFile.getBytes();
            // 파일업로드
            Files.write(uploadPaths, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FileRequest.builder()
                .fileIdx(fileIdx)
                .fileOriginName(multipartFile.getOriginalFilename())
                .fileNewName(fileNewName)
                .fileSize(multipartFile.getSize())
                .fileRegDate(fileRegDate)
                .filePath("/attachment/" + cate + "/" + today + "/" + fileNewName)
                .build();
    }

    /**
     * 저장 파일명 생성
     * @param filename 원본 파일명
     * @return 디스크에 저장할 파일명
     */
    private String generateSaveFilename(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

    /**
     * 업로드 경로 반환
     * @return 업로드 경로
     */
    private String getUploadPath() {
        return makeDirectories(uploadPath);
    }

    /**
     * 업로드 경로 반환
     * @param addPath - 추가 경로
     * @return 업로드 경로
     */
    private String getUploadPath(String cateDirPath, final String addPath) {
        return makeDirectories(cateDirPath + File.separator + addPath);
    }

    /**
     * 업로드 폴더(디렉터리) 생성
     * @param path - 업로드 경로
     * @return 업로드 경로
     */
    private String makeDirectories(final String path) {
        String staticPath = Paths.get(path).toString();
        System.out.println(staticPath);
        File dir = new File(staticPath);
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        return dir.getPath();
    }

    /**
     * 카테고리 폴더(디렉터리) 생성
     * @param path - 업로드 경로
     * @return 업로드 경로
     */
    private String makeCateDirectories(final String path) {
        String staticPath = Paths.get(path).toString();
        System.out.println(staticPath);
        File dir = new File(staticPath);
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        return dir.getPath();
    }
}
