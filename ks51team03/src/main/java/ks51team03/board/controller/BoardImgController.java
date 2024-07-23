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
import ks51team03.board.dto.NBoardImg;
import ks51team03.board.dto.NoticeBoard;
import ks51team03.board.mapper.BoardMapper;
import ks51team03.board.service.BoardService;
import ks51team03.files.dto.FileRequest;

@RestController
public class BoardImgController {

    @Autowired
    private BoardMapper boardmapper;
    
    @Autowired
    private BoardService boardService;
    
    @PostMapping("/board/imageUpload")
    public void imageUpload(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam("upload") MultipartFile upload,
                            @RequestParam(value = "nboardCode", required = false, defaultValue = "") String nboardCode) throws Exception {
        UUID uid = UUID.randomUUID();

        OutputStream out = null;
        PrintWriter printWriter = null;

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        try {
            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();

            String nbcode = nboardCode;
            System.out.println("nboardCode:"+nbcode);
            if (nboardCode.equals(""))
            {
            	nbcode="nb" + String.valueOf(boardmapper.getNBoardCode()+1);
            	System.out.println("nboardCode is null:"+nbcode);
            }
            
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

            String areadyImg=boardService.getNBoardImgByNBCode(nbcode);
            System.out.println("areadyImg:"+areadyImg);
            if ("".equals(nboardCode)&&areadyImg==null)
            {
            	NoticeBoard noticeBoard = new NoticeBoard();
                noticeBoard.setNboardCode(nbcode);
                noticeBoard.setMemberId("id66");
                noticeBoard.setBoardCode("b3");
                noticeBoard.setBoardCateCode("bct3");
                noticeBoard.setNboardTitle("temp");
                noticeBoard.setNboardContent("temp");
                noticeBoard.setNboardRegistDate("temp");
                noticeBoard.setNboardImg(fileUrl);

                // NoticeBoard 객체를 데이터베이스에 삽입
                boardService.insertNBoard(noticeBoard);
                
                nbcode = "nb" + String.valueOf(boardmapper.getNBoardCode());
            }
            
            
            // nboard_img 테이블에 이미지 정보 삽입
            NBoardImg nBoardImg = new NBoardImg();
     
            nBoardImg.setNbCode(nbcode);
            nBoardImg.setFilePath(fileUrl);
            nBoardImg.setNBoardImgFile(upload);
            FileRequest fileRequest = boardService.upLoadImgByNBCode(nBoardImg, nbcode);

            // JSON 응답을 올바르게 생성
            String jsonResponse = String.format("{\"uploaded\": 1, \"fileName\": \"%s\", \"url\": \"%s\"}", fileName, fileRequest.getFilePath());

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
    
    @GetMapping("/attachments/**")
    public void getImage(HttpServletRequest request, HttpServletResponse response) {
        
    	System.out.println("test");
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
