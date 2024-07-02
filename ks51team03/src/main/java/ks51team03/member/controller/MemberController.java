package ks51team03.member.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ks51team03.member.dto.Member;
import ks51team03.member.dto.MemberLevel;
import ks51team03.member.dto.Search;
import ks51team03.member.mapper.MemberMapper;
import ks51team03.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
	
	private final MemberService memberService;
	private final MemberMapper memberMapper;
	
	@PostMapping("/login")
	@ResponseBody
	public boolean login(@RequestParam(value = "memberId") String memberId
						, @RequestParam(value = "memberPw") String memberPw
						, HttpServletRequest request
						, HttpServletResponse response
						, HttpSession session){
		
		
		//String viewName="/login";
		Map<String, Object> checkMap = memberService.checkMemberInfo(memberId, memberPw);
		boolean isCheck = (boolean) checkMap.get("isCheck");
		if(isCheck) {
			Member memberInfo = (Member) checkMap.get("memberInfo");

			// 1. session
			//HttpSession requestGetSession = request.getSession();  이렇게 가져올 수도 있다.
			session.setAttribute("SID", memberInfo.getMemberId());
			session.setAttribute("SNAME", memberInfo.getMemberName());
			session.setAttribute("SLEVEL", memberInfo.getMemberLevel());

			String ccode = memberService.getCompanyCodeByMemberId(memberInfo.getMemberId());
			session.setAttribute("CCODE", ccode);
			log.info("Session CCODE set: {}", ccode);

			// 2. cookies
			Cookie cookie = new Cookie("loginId", memberInfo.getMemberId());
			cookie.setPath("/");
			cookie.setHttpOnly(true);
			cookie.setMaxAge(60*60*1);  // 1시간 유지

			// 생성된 쿠키를 응답객체에 담아 반환
			response.addCookie(cookie);
		}
		
        return isCheck;
    }

	@GetMapping("/logout")
	public String logout(HttpSession session, HttpServletResponse response){
		session.invalidate();

		// 2. cookie
		Cookie cookie = new Cookie("loginId", null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/member/member_main";
	}

	
	  @GetMapping("/login") 
	  public String login(Model model, @RequestParam(value="alertMsg", required = false) String alertMsg
	  								 ,@RequestParam(value = "currentPage", required = false) String currentPage ) 
	  {
	  if (alertMsg != null) { model.addAttribute("alertMsg", alertMsg); }
	  
	  System.out.println("로그인 버튼 누르기!!");
	  
	  return "redirect:"+currentPage;
	  }
	 
	
	@PostMapping("/idCheck")
	@ResponseBody
	public boolean idCheck(@RequestParam(value="memberId") String memberId) {
		log.info("아이디중복체크: memberId {}", memberId);
		boolean isMember = false;
		
		isMember = memberMapper.idCheck(memberId);
		
		return isMember;
	}
	
	@PostMapping("/member/insertMember")
	public String addMember(Member member) {
		
		log.info("회원가입 Member:{}", member);
		
		memberService.addMember(member);
		
		return "redirect:/member/member_main";
	}
	
	@GetMapping("/addMember")
	public String addMember(Model model) {
		
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원가입");
		model.addAttribute("levelList", memberLevelList);
		
		return "member/member_main";
	}
	
	@GetMapping("/member_main")
	public String userMainPage(Model model, @RequestParam(value="alertMsg", required = false) String alertMsg)
	{
		model.addAttribute("title","PAL");
		if(alertMsg != null) model.addAttribute("alertMsg", alertMsg);
		
		return "member/member_main";
	}
	
	@GetMapping("/member_login_terms_mem")
	public String userTermsPageMem(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_login_terms_mem";
	}
	
	@GetMapping("/member_login_terms_com")
	public String userTermsPageCom(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_login_terms_com";
	}
	
	@GetMapping("/member_login_insert_mem")
	public String userInsertPageMem(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_login_insert_mem";
	}
	
	@GetMapping("/member_login_insert_com")
	public String userInsertPageCom(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_login_insert_com";
	}
	
	@GetMapping("/member_login_insert_pet")
	public String userInsertPagePet(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_login_insert_pet";
	}
}
