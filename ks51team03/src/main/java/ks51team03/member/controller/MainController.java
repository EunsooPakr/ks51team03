package ks51team03.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ks51team03.board.dto.NoticeBoard;
import ks51team03.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {
	private final BoardService boardService;
	
	
	@GetMapping("/")
	public String userMainPage(Model model)
	{
		//공지사항 게시글 가져오기
		List<NoticeBoard> mainNBList = boardService.getMainNoticeBoard("b1");
		model.addAttribute("mainNBList",mainNBList);

		//추천순 게시글 가져오기
		List<NoticeBoard> mainRECList = boardService.getMainRecBoard("");
		model.addAttribute("mainRECList",mainRECList);
		
		//조회순 게시글 가져오기
		List<NoticeBoard> mainViewList = boardService.getMainViewBoard("");
		model.addAttribute("mainViewList",mainViewList);
		
		//최신순 게시글 가져오기
		List<NoticeBoard> mainLatestList = boardService.getMainLatestBoard("");
		model.addAttribute("mainLatestList",mainLatestList);
		
		
		return "index";
	}
}
