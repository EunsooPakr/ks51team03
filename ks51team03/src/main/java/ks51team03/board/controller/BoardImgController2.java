package ks51team03.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ks51team03.board.mapper.BoardMapper;

@RestController
public class BoardImgController2 {

    @Autowired
    private BoardMapper boardmapper;

	/* @PostMapping("/board/imageUpload") */
    public void imageUpload(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam("upload") MultipartFile upload) throws Exception {
        UUID uid = UUID.randomUUID();

        OutputStream out = null;
        PrintWriter printWriter = null;

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        try {
            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();

            String nbcode = "nb" + String.valueOf(boardmapper.getNBoardCode());

            String path = "/home/ks51team03/attachment" + File.separator + nbcode;
            String ckUploadPath = path + File.separator + uid + "_" + fileName;

            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            out = new FileOutputStream(new File(ckUploadPath));
            out.write(bytes);
            out.flush();

            // 파일 URL을 상대 경로로 변경
            String fileUrl = "/attachments/" + nbcode + "/" + uid + "_" + fileName;
            System.out.println("File URL: " + fileUrl); // 업로드된 파일의 URL 출력

            // JSON 응답을 올바르게 생성
            String jsonResponse = String.format("{\"uploaded\": 1, \"fileName\": \"%s\", \"url\": \"%s\"}", fileName, fileUrl);

            printWriter = response.getWriter();
            printWriter.print(jsonResponse); // println 대신 print 사용
            printWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            String errorResponse = "{\"uploaded\": 0, \"error\": {\"message\": \"" + e.getMessage() + "\"}}";
            printWriter = response.getWriter();
            printWriter.print(errorResponse);
            printWriter.flush();
        } finally {
            if (out != null) out.close();
            if (printWriter != null) printWriter.close();
        }
    }

	/* @GetMapping("/attachments/**") */
    public void getImage(HttpServletRequest request, HttpServletResponse response) {
        String filePath = request.getRequestURI().replace("/attachments", "");
        File file = new File("/home/ks51team03/attachment" + filePath);

        if (file.exists()) {
            response.setContentType("image/jpeg");

            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream out = response.getOutputStream()) {

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
