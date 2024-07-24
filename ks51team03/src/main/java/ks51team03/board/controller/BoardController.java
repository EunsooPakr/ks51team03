package ks51team03.board.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import ks51team03.board.dto.NBoardImg;
import ks51team03.board.dto.NBoardSearch;
import ks51team03.board.dto.NoticeBoard;
import ks51team03.board.mapper.BoardMapper;
import ks51team03.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	private final BoardService boardService;
	private final BoardMapper boardmapper;

	

	@GetMapping("/board_list_normal")
	public String getNoticeBoardList(Model model,
			@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(name = "searchType", required = false, defaultValue = "nb_title") String searchType,
			@RequestParam(name = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
			@RequestParam(name = "boardCateValue") String boardCateValue) {

		String boardTitle = boardCateValue; // 기본값 설정

		// 서비스 메서드 호출
		Map<String, Object> resultMap = boardService.getNoticeBoardList(boardCateValue, currentPage, searchType,
				searchKeyword);
		String boardCode = boardService.getBoardCodeByBCTValue(boardCateValue);
		String boardInfo = boardService.getBoardInfoByBCTValue(boardCateValue);

		// 결과를 모델에 담아서 뷰로 전달
		model.addAttribute("NoticeBoardList", resultMap.get("NoticeBoardList"));
		model.addAttribute("startPageNum", resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", resultMap.get("endPageNum"));
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("boardTitle", boardTitle); // boardTitle 변수를 모델에 추가
		model.addAttribute("boardCode", boardCode); // boardTitle 변수를 모델에 추가
		model.addAttribute("boardInfo", boardInfo); // boardTitle 변수를 모델에 추가
		// 검색 키워드 설정
		List<NBoardSearch> searchCate = new ArrayList<>();
		NBoardSearch search1 = new NBoardSearch();
		search1.setSearchKey("memberId");
		search1.setSearchText("회원아이디");
		NBoardSearch search2 = new NBoardSearch();
		search2.setSearchKey("nboardTitle");
		search2.setSearchText("제목");
		NBoardSearch search3 = new NBoardSearch();
		search3.setSearchKey("nboardContent");
		search3.setSearchText("내용");
		searchCate.add(search1);
		searchCate.add(search2);
		searchCate.add(search3);
		model.addAttribute("searchCate", searchCate);

		return "board/board_list_normal"; // 뷰 이름 리턴
	}

	@GetMapping("/board_list_normal_search")
	public String searchNoticeBoardList(Model model,
			@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage,
			@RequestParam(name = "searchType", required = false) String searchType,
			@RequestParam(name = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(name = "boardCateValue") String boardCateValue) {

		String boardTitle = boardCateValue; // 기본값 설정
		// 서비스 메서드 호출
		Map<String, Object> resultMap = boardService.getNoticeBoardList(boardCateValue, currentPage, searchType,
				searchKeyword);

		// 결과를 모델에 담아서 뷰로 전달
		model.addAttribute("NoticeBoardList", resultMap.get("NoticeBoardList"));
		model.addAttribute("startPageNum", resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", resultMap.get("endPageNum"));
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("boardTitle", boardTitle); // boardTitle 변수를 모델에 추가

		// 검색 키워드 설정
		List<NBoardSearch> searchCate = new ArrayList<>();
		NBoardSearch search1 = new NBoardSearch();
		search1.setSearchKey("memberId");
		search1.setSearchText("회원아이디");
		NBoardSearch search2 = new NBoardSearch();
		search2.setSearchKey("nboardTitle");
		search2.setSearchText("제목");
		NBoardSearch search3 = new NBoardSearch();
		search3.setSearchKey("nboardContent");
		search3.setSearchText("내용");
		searchCate.add(search1);
		searchCate.add(search2);
		searchCate.add(search3);
		model.addAttribute("searchCate", searchCate);

		return "board/board_list_normal"; // 뷰 이름 리턴
	}

	/* 자유게시글 작성 */
	@PostMapping("/board_write_normal")
	public String boardWriteNormalPage(NoticeBoard nboard, RedirectAttributes rttr) {
		log.info("board_write_normal");

		String boardCateCodeName = nboard.getBoardCateCode();
		String boardCateCode = boardService.getBoardCateCodeByBCTValue(nboard);
		String boardCode = boardService.getBoardCodeByNBoard(nboard);

		nboard.setBoardCateCode(boardCateCode);
		nboard.setBoardCode(boardCode);
		boardService.insertNBoard(nboard);

		String encodedBoardCateValue = UriUtils.encodeQueryParam(boardCateCodeName, StandardCharsets.UTF_8);
		return "redirect:/board/board_list_normal?currentPage=1&boardCateValue=" + encodedBoardCateValue;
	}

	@GetMapping("/board_write_normal")
	public String boardWriteNormalPage(Model model, @RequestParam(name = "boardCateValue") String boardCateValue) {

		String boardTitle = boardCateValue; // 기본값 설정
		String boardInfo = boardService.getBoardInfoByBCTValue(boardCateValue);

		model.addAttribute("boardTitle", boardTitle); // boardTitle 변수를 모델에 추가
		model.addAttribute("boardInfo", boardInfo); // boardTitle 변수를 모델에 추가
		
		return "board/board_write_normal";
	}


	/* 자유 게시글 열람 */
	@GetMapping("/board_view_normal")
	public String boardViewNormalPage(@RequestParam(value = "nboardCode") String nboardCode,Model model) {
		log.info("board_view_normal");

		// 게시글 코드를 숫자만 분리해서 넘기기
		// int nbcode=Integer.parseInt(nboardCode.replaceAll("[^\\d]", ""));
		String boardTitle = boardService.getBCTValueByNBCode(nboardCode); // 기본값 설정
		String boardInfo = boardService.getBoardInfoByBCTValue(boardTitle);

		model.addAttribute("boardTitle", boardTitle); // boardTitle 변수를 모델에 추가
		model.addAttribute("boardInfo", boardInfo); // boardTitle 변수를 모델에 추가
		model.addAttribute("noticeBoard", boardService.getNBoardByNBCode(nboardCode));
		// 조회수 증가
		boardService.increaseViewByNBCode(nboardCode);
		return "board/board_view_normal";
	}

	/* 자유 게시글 추천 */
	@PostMapping("/recommend")
	@ResponseBody
	public ResponseEntity<String> recommendBoard(@RequestParam(value = "nboardCode") String nboardCode) {
		boardService.increaseRecByNBCode(nboardCode);

		// 클라이언트에게 성공 메시지 반환
		return ResponseEntity.ok("게시물을 추천하였습니다.");
	}

	/* 자유 게시글 수정 전 읽어오기 */
	@GetMapping("/board_edit_normal")
	public String boardEditNormalPage(@RequestParam(value = "nboardCode") String nboardCode,
			@RequestParam(name = "boardCateValue") String boardCateValue, Model model) {
		log.info("board_edit_normal");

		String boardTitle = boardCateValue; // 기본값 설정
		String boardInfo = boardService.getBoardInfoByBCTValue(boardCateValue);

		model.addAttribute("boardTitle", boardTitle); // boardTitle 변수를 모델에 추가
		model.addAttribute("boardInfo", boardInfo); // boardTitle 변수를 모델에 추가
		model.addAttribute("noticeBoard", boardService.getNBoardByNBCode(nboardCode));

		return "board/board_edit_normal";
	}

	/* 자유 게시글 수정 */
	@PostMapping("/board_edit_normal")
	public String boardEditNormalPage(NoticeBoard nboard, RedirectAttributes rttr) {
		log.info("board_edit_normal");

		String BoardCateValueName = boardService.getBCTValueNameByBCTCode(nboard);

		boardService.updateNBoard(nboard);
		// 직접 URL 인코딩을 수행하여 파라미터를 추가
		/*
		 * String encodedBoardCateValue = UriUtils.encodeQueryParam("자유 게시글",
		 * StandardCharsets.UTF_8);
		 */
		String encodedBoardCateValue = UriUtils.encodeQueryParam(BoardCateValueName, StandardCharsets.UTF_8);
		return "redirect:/board/board_list_normal?currentPage=1&boardCateValue=" + encodedBoardCateValue;
	}
	
	
	
	@GetMapping("/board_list_gallery")
	public String getGalleryBoardList(Model model,
	                                  @RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
	                                  @RequestParam(name = "searchType", required = false, defaultValue = "nb_title") String searchType,
	                                  @RequestParam(name = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
	                                  @RequestParam(name = "boardCateValue") String boardCateValue) {

	    String boardTitle = boardCateValue; // 기본값 설정

	    // 서비스 메서드 호출
	    Map<String, Object> resultMap = boardService.getNoticeBoardList(boardCateValue, currentPage, searchType, searchKeyword);
	    String boardCode = boardService.getBoardCodeByBCTValue(boardCateValue);
	    String boardInfo = boardService.getBoardInfoByBCTValue(boardCateValue);

	    
	    
	    // 결과를 모델에 담아서 뷰로 전달
	    model.addAttribute("NoticeBoardList", resultMap.get("NoticeBoardList"));
	    model.addAttribute("startPageNum", resultMap.get("startPageNum"));
	    model.addAttribute("endPageNum", resultMap.get("endPageNum"));
	    model.addAttribute("lastPage", resultMap.get("lastPage"));
	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("boardTitle", boardTitle); // boardTitle 변수를 모델에 추가
	    model.addAttribute("boardCode", boardCode); // boardCode 변수를 모델에 추가
	    model.addAttribute("boardInfo", boardInfo); // boardInfo 변수를 모델에 추가

	    // 검색 키워드 설정
	    List<NBoardSearch> searchCate = new ArrayList<>();
	    NBoardSearch search1 = new NBoardSearch();
	    search1.setSearchKey("memberId");
	    search1.setSearchText("회원아이디");
	    NBoardSearch search2 = new NBoardSearch();
	    search2.setSearchKey("nboardTitle");
	    search2.setSearchText("제목");
	    NBoardSearch search3 = new NBoardSearch();
	    search3.setSearchKey("nboardContent");
	    search3.setSearchText("내용");
	    searchCate.add(search1);
	    searchCate.add(search2);
	    searchCate.add(search3);
	    model.addAttribute("searchCate", searchCate);

	    return "board/board_list_gallery"; // 뷰 이름 리턴
	}

	
	
	
	/*
	@GetMapping("/board_list_gallery")
	public String getGalleryBoardList(Model model,
			@RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(name = "searchType", required = false, defaultValue = "nb_title") String searchType,
			@RequestParam(name = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
			@RequestParam(name = "boardCateValue") String boardCateValue) {

		String boardTitle = boardCateValue; // 기본값 설정

		// 서비스 메서드 호출
		Map<String, Object> resultMap = boardService.getNoticeBoardList(boardCateValue, currentPage, searchType,
				searchKeyword);
		String boardCode = boardService.getBoardCodeByBCTValue(boardCateValue);
		String boardInfo = boardService.getBoardInfoByBCTValue(boardCateValue);

		// 결과를 모델에 담아서 뷰로 전달
		model.addAttribute("NoticeBoardList", resultMap.get("NoticeBoardList"));
		model.addAttribute("startPageNum", resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", resultMap.get("endPageNum"));
		model.addAttribute("lastPage", resultMap.get("lastPage"));
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("boardTitle", boardTitle); // boardTitle 변수를 모델에 추가
		model.addAttribute("boardCode", boardCode); // boardTitle 변수를 모델에 추가
		model.addAttribute("boardInfo", boardInfo); // boardTitle 변수를 모델에 추가
		// 검색 키워드 설정
		List<NBoardSearch> searchCate = new ArrayList<>();
		NBoardSearch search1 = new NBoardSearch();
		search1.setSearchKey("memberId");
		search1.setSearchText("회원아이디");
		NBoardSearch search2 = new NBoardSearch();
		search2.setSearchKey("nboardTitle");
		search2.setSearchText("제목");
		NBoardSearch search3 = new NBoardSearch();
		search3.setSearchKey("nboardContent");
		search3.setSearchText("내용");
		searchCate.add(search1);
		searchCate.add(search2);
		searchCate.add(search3);
		model.addAttribute("searchCate", searchCate);

		return "board/board_list_gallery"; // 뷰 이름 리턴
	}*/
	
	/* 갤러리게시글 작성 */
	@GetMapping("/board_write_gallery")
	public String boardWriteGalleryPage(Model model,@RequestParam(name = "boardCateValue") String boardCateValue)
	{
		String boardTitle = boardCateValue; // 기본값 설정
		String boardInfo = boardService.getBoardInfoByBCTValue(boardCateValue);

		String nboardCode="nb" + String.valueOf(boardmapper.getNBoardCode()+1);
		
		model.addAttribute("boardTitle", boardTitle); // boardTitle 변수를 모델에 추가
		model.addAttribute("boardInfo", boardInfo); // boardTitle 변수를 모델에 추가
		model.addAttribute("nboardCode", nboardCode); // nboardCode 변수를 모델에 추가
		
		return "board/board_write_gallery";
	}

	@PostMapping("/board_write_gallery")
	public String boardWriteGalleryPage(NoticeBoard nboard, RedirectAttributes rttr) {
	    log.info("board_write_gallery");

	    String boardCateCodeName = nboard.getBoardCateCode();
	    String boardCateCode = boardService.getBoardCateCodeByBCTValue(nboard);
	    String boardCode = boardService.getBoardCodeByNBoard(nboard);
	    
	    nboard.setBoardCateCode(boardCateCode);
	    nboard.setBoardCode(boardCode);
	    String nbcode = "nb" + String.valueOf(boardmapper.getNBoardCode());
	    
	    nboard.setNboardCode(nbcode);

	    
	    String boardImgUrl=boardService.getNBoardImgByNBCode(nbcode);
	    nboard.setNboardImg(boardImgUrl);
	    System.out.println("boardImgUrl: " + boardImgUrl);
	    System.out.println("contents:"+nboard.getNboardContent());
	    // 대표 이미지가 내용에서 지워질 때까지 반복
	    while (!nboard.getNboardContent().contains(boardImgUrl)) {
	        boardService.deleteFilesAndNBoardImg(boardImgUrl);
	        System.out.println("Deleted: " + boardImgUrl);

	        // 이미지가 삭제된 후 업데이트된 내용을 다시 가져옵니다.
	        boardImgUrl = boardService.getNBoardImgByNBCode(nbcode);
	        nboard.setNboardImg(boardImgUrl);
	        System.out.println("New boardImgUrl: " + boardImgUrl);

	        // 만약 더 이상 삭제할 이미지가 없다면 루프를 종료합니다.
	        if (boardImgUrl == null || boardImgUrl.isEmpty()) {
	        	System.out.println("boardImgUrl.isEmpty()");
	            break;
	        }
	    }
	    
	    boardService.updateNBoard(nboard);
	    
	    String encodedBoardCateValue = UriUtils.encodeQueryParam(boardCateCodeName, StandardCharsets.UTF_8);
	    

	    // 이미지 업로드 로직은 필요하지 않음
	    // boardService.upLoadImgByNBCode(nboardimg, nbcode);

	    return "redirect:/board/board_list_gallery?currentPage=1&boardCateValue=" + encodedBoardCateValue;
	}


	/* 갤러리 게시글 열람 */
	@GetMapping("/board_view_gallery")
	public String boardGalleryNormalPage(@RequestParam(value = "nboardCode") String nboardCode,Model model) {
		log.info("board_view_gallery");

		// 게시글 코드를 숫자만 분리해서 넘기기
		// int nbcode=Integer.parseInt(nboardCode.replaceAll("[^\\d]", ""));
		String boardTitle = boardService.getBCTValueByNBCode(nboardCode); // 기본값 설정
		String boardInfo = boardService.getBoardInfoByBCTValue(boardTitle);

		model.addAttribute("boardTitle", boardTitle); // boardTitle 변수를 모델에 추가
		model.addAttribute("boardInfo", boardInfo); // boardTitle 변수를 모델에 추가
		model.addAttribute("noticeBoard", boardService.getNBoardByNBCode(nboardCode));
		// 조회수 증가
		boardService.increaseViewByNBCode(nboardCode);
		return "board/board_view_gallery";
	}
	
	/* 자유 게시글 수정 전 읽어오기 */
	@GetMapping("/board_edit_gallery")
	public String boardEditGalleryPage(@RequestParam(value = "nboardCode") String nboardCode,
			@RequestParam(name = "boardCateValue") String boardCateValue, Model model) {
		log.info("board_edit_normal");

		String boardTitle = boardCateValue; // 기본값 설정
		String boardInfo = boardService.getBoardInfoByBCTValue(boardCateValue);

		model.addAttribute("boardTitle", boardTitle); // boardTitle 변수를 모델에 추가
		model.addAttribute("boardInfo", boardInfo); // boardTitle 변수를 모델에 추가
		model.addAttribute("noticeBoard", boardService.getNBoardByNBCode(nboardCode));

		return "board/board_edit_gallery";
	}

	/* 자유 게시글 수정 */
	@PostMapping("/board_edit_gallery")
	public String boardEditGalleryPage(NoticeBoard nboard, RedirectAttributes rttr) {
		log.info("board_edit_normal");

		String BoardCateValueName = boardService.getBCTValueNameByBCTCode(nboard);
		String nbcode =nboard.getNboardCode();
		 
		String boardImgUrl=boardService.getNBoardImgByNBCode(nbcode);
	    nboard.setNboardImg(boardImgUrl);
	    System.out.println("boardImgUrl: " + boardImgUrl);
	    System.out.println("contents:"+nboard.getNboardContent());
	    // 대표 이미지가 내용에서 지워질 때까지 반복
	    if (boardImgUrl != null && nboard.getNboardContent() != null) {
		    while (!nboard.getNboardContent().contains(boardImgUrl)) {
		        boardService.deleteFilesAndNBoardImg(boardImgUrl);
		        System.out.println("Deleted: " + boardImgUrl);
	
		        // 이미지가 삭제된 후 업데이트된 내용을 다시 가져옵니다.
		        boardImgUrl = boardService.getNBoardImgByNBCode(nbcode);
		        nboard.setNboardImg(boardImgUrl);
		        System.out.println("New boardImgUrl: " + boardImgUrl);
	
		        // 만약 더 이상 삭제할 이미지가 없다면 루프를 종료합니다.
		        if (boardImgUrl == null || boardImgUrl.isEmpty()) {
		        	System.out.println("boardImgUrl.isEmpty()");
		            break;
		        }
		    }
	    }
	    boardService.updateNBoard(nboard);
	    
		String encodedBoardCateValue = UriUtils.encodeQueryParam(BoardCateValueName, StandardCharsets.UTF_8);
		return "redirect:/board/board_list_gallery?currentPage=1&boardCateValue=" + encodedBoardCateValue;
	}
	
	/*
	 * 자유 게시글 검색
	 * 
	 * @PostMapping("/board_search_list")
	 * 
	 * @ResponseBody public List<NoticeBoard> getSearchList(@RequestBody
	 * NBoardSearch nbsearch){
	 * 
	 * log.info("nbsearch: {}", nbsearch);
	 * 
	 * return boardService.getBoardSearchList(nbsearch); }
	 */
}