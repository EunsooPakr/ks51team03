package ks51team03.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	/*자유게시판 목록*/
	@GetMapping("/board_list_normal")
	public String boardListNormalPage(Model model)
	{
		String boardCateValue="자유 게시글";
		log.info("board_list_normal");
		model.addAttribute("normalList",boardService.getNoticeBoardList(boardCateValue));
		
		return "/board/board_list_normal";
	}
	
	/*자유게시판 작성*/
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
		model.addAttribute("title","PAL");
		
		return "/board/board_write_normal";
	}


	
	@GetMapping("/board_view_normal")
	public String boardViewNormalPage(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "/board/board_view_normal";
	}
	
	@GetMapping("/board_edit_normal")
	public String boardEditNormalPage(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "/board/board_edit_normal";
	}
}