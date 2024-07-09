package ks51team03.board.controller;

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

	/*자유 게시글 목록*/
	@GetMapping("/board_list_normal")
	public String boardListNormalPage(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
									Model model)
	{
		//게시글 불러오기
		String boardCateValue="자유 게시글";
		log.info("board_list_normal");
		//model.addAttribute("normalList",boardService.getNoticeBoardList(boardCateValue));
		Map<String, Object> resultMap = boardService.getNoticeBoardList(boardCateValue,currentPage);
		//페이징
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> NoticeBoardList = (List<Map<String, Object>>) resultMap.get("NoticeBoardList");
		int lastPage = (int) resultMap.get("lastPage");
		int startPageNum = (int) resultMap.get("startPageNum");
		int endPageNum = (int) resultMap.get("endPageNum");

		log.info("currentPage: {}", currentPage);
		log.info("startPageNum: {}", startPageNum);
		log.info("endPageNum: {}", endPageNum);
		log.info("lastPage: {}", lastPage);
		
		model.addAttribute("NoticeBoardList", NoticeBoardList);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("startPageNum", startPageNum);
		model.addAttribute("endPageNum", endPageNum);
		
		return "/board/board_list_normal";
	}
	
	/*자유게시글 작성*/
	@PostMapping("/board_write_normal")
	public String boardWriteNormalPage(NoticeBoard nboard, RedirectAttributes rttr)
	{
		log.info("board_write_normal");
		boardService.insertNBoard(nboard);
		return "redirect:/board/board_list_normal";
	}
	
	@GetMapping("/board_write_normal")
	public String boardWriteNormalPage(Model model)
	{
		return "/board/board_write_normal";
	}


	/*자유 게시글 열람*/
	@GetMapping("/board_view_normal")
	public String boardViewNormalPage(@RequestParam(value = "nboardCode") String nboardCode,Model model)
	{
		log.info("board_view_normal");
		
		//게시글 코드를 숫자만 분리해서 넘기기
		//int nbcode=Integer.parseInt(nboardCode.replaceAll("[^\\d]", ""));
		model.addAttribute("noticeBoard",boardService.getNBoardByNBCode(nboardCode));
		//조회수 증가
		boardService.increaseViewByNBCode(nboardCode);
		return "/board/board_view_normal";
	}
	
	 /*자유 게시글 추천*/
    @PostMapping("/recommend")
    @ResponseBody
    public ResponseEntity<String> recommendBoard(@RequestParam(value = "nboardCode") String nboardCode) {
    	boardService.increaseRecByNBCode(nboardCode);
        
        // 클라이언트에게 성공 메시지 반환
        return ResponseEntity.ok("게시물을 추천하였습니다.");
    }
	
    /*자유 게시글 수정 전 읽어오기*/
	@GetMapping("/board_edit_normal")
	public String boardEditNormalPage(@RequestParam(value = "nboardCode") String nboardCode,Model model)
	{
		log.info("board_edit_normal");
		model.addAttribute("noticeBoard",boardService.getNBoardByNBCode(nboardCode));
		
		return "/board/board_edit_normal";
	}
	
	/*자유 게시글 수정*/
	@PostMapping("/board_edit_normal")
	public String boardEditNormalPage(NoticeBoard nboard, RedirectAttributes rttr)
	{
		log.info("board_edit_normal");
		boardService.updateNBoard(nboard);
		return "redirect:/board/board_list_normal";
	}
}