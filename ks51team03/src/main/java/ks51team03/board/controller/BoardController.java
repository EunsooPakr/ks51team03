package ks51team03.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("boardCommonController")
public class BoardController {
	@GetMapping("/board/board_write_nomal")
	public String userMainPage(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "/board/board_write_nomal";
	}

}