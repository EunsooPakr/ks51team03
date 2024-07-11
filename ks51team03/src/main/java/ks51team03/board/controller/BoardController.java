package ks51team03.board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ks51team03.board.dto.NBoardSearch;
import ks51team03.board.dto.NoticeBoard;
import ks51team03.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	private final BoardService boardService;

	@GetMapping("/board_list_normal")
    public String getNoticeBoardList(Model model,
                                     @RequestParam(name = "currentPage", defaultValue = "1") int currentPage,
                                     @RequestParam(name = "searchType", required = false, defaultValue = "nb_title") String searchType,
                                     @RequestParam(name = "searchKeyword", required = false, defaultValue = "") String searchKeyword,
                                     @RequestParam(name = "boardCateValue") String boardCateValue) {

		String boardTitle = "자유 게시글"; // 기본값 설정

        // 서비스 메서드 호출
        Map<String, Object> resultMap = boardService.getNoticeBoardList(boardCateValue, currentPage, searchType, searchKeyword);

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

    @GetMapping("/board_list_normal_search")
    public String searchNoticeBoardList(Model model,
                                        @RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage,
                                        @RequestParam(name = "searchType", required = false) String searchType,
                                        @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
    									@RequestParam(name = "boardCateValue") String boardCateValue){


    	String boardTitle = "자유 게시글"; // 기본값 설정
    	
        // 서비스 메서드 호출
        Map<String, Object> resultMap = boardService.getNoticeBoardList(boardCateValue, currentPage, searchType, searchKeyword);

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
		boardService.insertNBoard(nboard);
		return "redirect:/board/board_list_normal";
	}

	@GetMapping("/board_write_normal")
	public String boardWriteNormalPage(Model model) {
		return "/board/board_write_normal";
	}

	/* 자유 게시글 열람 */
	@GetMapping("/board_view_normal")
	public String boardViewNormalPage(@RequestParam(value = "nboardCode") String nboardCode, Model model) {
		log.info("board_view_normal");

		// 게시글 코드를 숫자만 분리해서 넘기기
		// int nbcode=Integer.parseInt(nboardCode.replaceAll("[^\\d]", ""));
		model.addAttribute("noticeBoard", boardService.getNBoardByNBCode(nboardCode));
		// 조회수 증가
		boardService.increaseViewByNBCode(nboardCode);
		return "/board/board_view_normal";
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
	public String boardEditNormalPage(@RequestParam(value = "nboardCode") String nboardCode, Model model) {
		log.info("board_edit_normal");
		model.addAttribute("noticeBoard", boardService.getNBoardByNBCode(nboardCode));

		return "/board/board_edit_normal";
	}

	/* 자유 게시글 수정 */
	@PostMapping("/board_edit_normal")
	public String boardEditNormalPage(NoticeBoard nboard, RedirectAttributes rttr) {
		log.info("board_edit_normal");
		boardService.updateNBoard(nboard);

		return "redirect:/board/board_list_normal";
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