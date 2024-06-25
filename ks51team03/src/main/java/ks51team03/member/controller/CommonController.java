package ks51team03.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("userCommonController")
public class CommonController {
	@GetMapping("/member/member_main")
	public String userMainPage(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_main";
	}
	
	@GetMapping("/member/member_login_terms_mem")
	public String userTermsPageMem(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_login_terms_mem";
	}
	
	@GetMapping("/member/member_login_terms_com")
	public String userTermsPageCom(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_login_terms_com";
	}
	
	@GetMapping("/member/member_login_insert_mem")
	public String userInsertPageMem(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_login_insert_mem";
	}
	
	@GetMapping("/member/member_login_insert_com")
	public String userInsertPageCom(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_login_insert_com";
	}
	
	@GetMapping("/member/member_login_insert_pet")
	public String userInsertPagePet(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_login_insert_pet";
	}
}
