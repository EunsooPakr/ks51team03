package ks51team03.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("boardCommonController")
public class BoardController {
	@GetMapping("/board/board_write_normal")
	public String boardWriteNormalPage(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "/board/board_write_normal";
	}

	
	@GetMapping("/board/board_list_normal")
	public String boardListNormalPage(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "/board/board_list_normal";
	}
	
	@GetMapping("/board/board_view_normal")
	public String boardViewNormalPage(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "/board/board_view_normal";
	}
	
	@GetMapping("/board/board_edit_normal")
	public String boardEditNormalPage(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "/board/board_edit_normal";
	}
}