package ks51team03.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ks51team03.company.dto.*;
import ks51team03.member.dto.MemberLike;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ks51team03.company.service.CompanyService;
import ks51team03.member.dto.Member;
import ks51team03.member.dto.MemberLevel;
import ks51team03.member.mapper.MemberMapper;
import ks51team03.member.service.MemberService;
import ks51team03.pet.dto.Pet;
import ks51team03.pet.mapper.PetMapper;
import ks51team03.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private final MemberMapper memberMapper;
	private final CompanyService companyService;
	private final PetService petService;
	private final PetMapper petMapper;

	
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
			int informCount = memberService.getInformCount(memberInfo.getMemberId());
			List<ComInformReciPient> getInform = memberService.getInform(memberInfo.getMemberId());


			// 1. session
			//HttpSession requestGetSession = request.getSession();  이렇게 가져올 수도 있다.
			session.setAttribute("SID", memberInfo.getMemberId());
			session.setAttribute("SNAME", memberInfo.getMemberName());
			session.setAttribute("SLEVEL", memberInfo.getMemberLevel());
			session.setAttribute("SINFOCOUNT", informCount);
			session.setAttribute("SINFORM", getInform);


			String ccode = memberService.getCompanyCodeByMemberId(memberInfo.getMemberId());
			if(ccode == null) {
				ccode = companyService.getCompanyCodeByMemberId(memberInfo.getMemberId());
			}


			session.setAttribute("CCODE", ccode);


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

	@PostMapping("/login/gara")
	public String loginGara(@RequestParam(value = "headerId") String memberId
			, @RequestParam(value = "headerPw") String memberPw
			, HttpServletRequest request
			, HttpServletResponse response
			, HttpSession session){


		//String viewName="/login";
		Map<String, Object> checkMap = memberService.checkMemberInfo(memberId, memberPw);
		boolean isCheck = (boolean) checkMap.get("isCheck");
		if(isCheck) {
			Member memberInfo = (Member) checkMap.get("memberInfo");
			int informCount = memberService.getInformCount(memberInfo.getMemberId());
			List<ComInformReciPient> getInform = memberService.getInform(memberInfo.getMemberId());


			// 1. session
			//HttpSession requestGetSession = request.getSession();  이렇게 가져올 수도 있다.
			session.setAttribute("SID", memberInfo.getMemberId());
			session.setAttribute("SNAME", memberInfo.getMemberName());
			session.setAttribute("SLEVEL", memberInfo.getMemberLevel());
			session.setAttribute("SINFOCOUNT", informCount);
			session.setAttribute("SINFORM", getInform);


			String ccode = memberService.getCompanyCodeByMemberId(memberInfo.getMemberId());
			if(ccode == null) {
				ccode = companyService.getCompanyCodeByMemberId(memberInfo.getMemberId());
			}


			session.setAttribute("CCODE", ccode);


			// 2. cookies
			Cookie cookie = new Cookie("loginId", memberInfo.getMemberId());
			cookie.setPath("/");
			cookie.setHttpOnly(true);
			cookie.setMaxAge(60*60*1);  // 1시간 유지

			// 생성된 쿠키를 응답객체에 담아 반환
			response.addCookie(cookie);
		}

		return "redirect:/";
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
		return "redirect:/";
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

		boolean isMember = false;
		
		isMember = memberMapper.idCheck(memberId);
		
		return isMember;
	}
	
	@PostMapping("/insertMember")
	public String insertMember(Member member,HttpSession session) {
		
		String nextPage="redirect:/";

		//반려동물이 있는 경우
		if(member.getMemberPet()>0)
		{
			//session.setAttribute("SID", member.getMemberId());
			session.setAttribute("SMEM", member);
			nextPage="redirect:/member/member_login_insert_pet";
		}
		else
		{

			memberService.insertMember(member);
		}
		
		return nextPage;
	}
	
	@GetMapping("/insertMember")
	public String insertMember(Model model) {
		
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원가입");
		model.addAttribute("levelList", memberLevelList);
		
		return "member/member_login_insert_mem";
	}
	
	@PostMapping("/ceoIdCheck")
	@ResponseBody
	public boolean ceoIdCheck(@RequestParam(value = "memberId") String memberId
						, @RequestParam(value = "memberPw") String memberPw
						, HttpServletRequest request
						, HttpServletResponse response){

		Map<String, Object> checkMap = memberService.checkMemberInfo(memberId, memberPw);
		boolean isCheck = (boolean) checkMap.get("isCheck");
		
        return isCheck;
    }
	
	
	@GetMapping("/index")
	public String userMainPage(Model model, @RequestParam(value="alertMsg", required = false) String alertMsg)
	{
		model.addAttribute("title","PAL");
		if(alertMsg != null) model.addAttribute("alertMsg", alertMsg);
		
		return "index";
	}
	
	@GetMapping("/member_mypage_main")
	public String userMyPageMain(Model model)
	{
		model.addAttribute("title","member_mypage_main");
		
		return "member/member_mypage_main";
	}
	
	@GetMapping("/member_mypage_memberinfo")
	public String userMyPageMemberInfo(Model model,HttpSession session, RedirectAttributes redirectAttributes)
	{
		String memberId=(String) session.getAttribute("SID");
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}
		Member memberInfo = memberService.getMemberInfoById(memberId);
		
		model.addAttribute("memberInfo",memberInfo);
		
		return "member/member_mypage_memberinfo";
	}

	@PostMapping("/updateMember")
	public String modifyMember(Member member) {

		memberService.updateMember(member);
		return "redirect:/member/member_mypage_memberinfo";
	}
	
	@GetMapping("/updateMember")
	public String modifyMember(@RequestParam(value="memberId") String memberId
							  ,Model model) {
		Member memberInfo = memberService.getMemberInfoById(memberId);
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원수정");
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("levelList", memberLevelList);
		
		return "member/member_login_update_mem";
	}
	
	@GetMapping("/member_mypage_myQandR")
	public String userMyPageMyQandR(Model model,HttpSession session, RedirectAttributes redirectAttributes)
	{
		String memberId=(String) session.getAttribute("SID");
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}
		List<ComQuestion> memberQuestion = memberService.getQuestionById(memberId);
		List<ComReview> comReviews = memberService.getCompanyReview(memberId);
		model.addAttribute("memberQuestion",memberQuestion);
		model.addAttribute("comReviews",comReviews);

		return "member/member_mypage_myQandR";
	}

	@GetMapping("/member_mypage_question_modify")
	public String userQuestionModify(@RequestParam("quesnum") String quesNum, Model model) {
		ComQuestion question = companyService.getCompanyQuestionById(quesNum);
		model.addAttribute("question", question);

		return "member/member_mypage_question_modify";
	}

	@PostMapping("/member_question_modify")
	public String userQuestionModifyAction(@RequestParam("quesNum") String quesNum,ComQuestion question) {
		String qctenum = getQctenum(question.getQcteNum());
		question.setQuesNum(quesNum);
		question.setQcteNum(qctenum);
		memberService.memberQuestionModify(question);
		return "redirect:/member/member_mypage_myQandR";
	}

	private String getQctenum(String qcteNum) {
		Map<String, String> qctenumMap = new HashMap<>();
		qctenumMap.put("병원이용", "qctc1");
		qctenumMap.put("기타문의", "qctc11");
		qctenumMap.put("진료관련", "qctc2");
		qctenumMap.put("병원행정", "qctc3");
		qctenumMap.put("약국행정", "qctc4");
		qctenumMap.put("제품관련", "qctc5");
		qctenumMap.put("처방문의", "qctc9");
		qctenumMap.put("장례문의", "qctc7");
		qctenumMap.put("비용문의", "qctc8");
		qctenumMap.put("일정문의", "qctc9");

		return qctenumMap.getOrDefault(qcteNum, "기타문의");
	}

	@GetMapping("/member_mypage_question_delete")
	public String userQuestionDelete(@RequestParam("quesnum") String quesNum) {
		ComQuestion question = new ComQuestion();
		question.setQuesNum(quesNum);
		memberService.memberQuestionDelete(question);

		return "redirect:/member/member_mypage_myQandR";
	}

	@GetMapping("/member_mypage_review_modify")
	public String userReviewModify(@RequestParam("revcode") String revCode, Model model, HttpSession session) {
		ComReview review = memberService.getCompanyReviewByRevCode(revCode);
		model.addAttribute("review", review);

		return "member/member_mypage_review_modify";
	}

	@PostMapping("/member_review_modify")
	public String userReviewModifyAction(@RequestParam("revCode") String revCode, ComReview review,
										 @RequestParam(value = "deleteImage", defaultValue = "false") boolean deleteImage,
										 @RequestParam(value = "newImage", defaultValue = "false") boolean newImage,
										 @RequestParam("revImgFile") MultipartFile revImgFile) {
		review.setRevCode(revCode);
		memberService.memberReviewModify(review, deleteImage, newImage, revImgFile);
		return "redirect:/member/member_mypage_myQandR";
	}

	// 회원 리뷰 삭제
	@PostMapping("/member_mypage_review_delete")
	public String userReviewDeleteAction(@RequestParam("revCode") String revCode, ComReview review) {
		review.setRevCode(revCode);
		memberService.memberReviewDelete(review);

		return "redirect:/member/member_mypage_myQandR";
	}

	@GetMapping("/member_mypage_list_pet")
	public String userMyPageListPet(Model model,HttpSession session)
	{
		String memberId=(String) session.getAttribute("SID");
		List<Pet> petInfoList = petService.getPetInfoByMemberId(memberId);
		
		model.addAttribute("petInfoList",petInfoList);
		
		return "member/member_mypage_list_pet";
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

	@PutMapping("/disable/{informId}")
	public ResponseEntity<Void> disableNotification(@PathVariable String informId, HttpSession session) {
		try {
			String memberId = (String) session.getAttribute("SID");
			memberService.disableNotification(informId);
			List<ComInformReciPient> getInform = memberService.getInform(memberId);
			int informCount = memberService.getInformCount(memberId);
			session.setAttribute("SINFORM", getInform);
			session.setAttribute("SINFOCOUNT", informCount);

			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	@GetMapping("/member_main2")
	public String userTestPage(Model model)
	{
		model.addAttribute("title","PAL");
		
		return "member/member_main2";
	}
	
	@GetMapping("/member_login_find_id")
	public String userFindMemberIdPage(Model model)
	{
		return "member/member_login_find_id";
	}


	@GetMapping("/member_login_find_pw")
	public String userFindMemberPwPage(Model model)
	{
		return "member/member_login_find_pw";
	}

	@ResponseBody
	@PostMapping("/updatePw")
	public ResponseEntity<Boolean> updatePw(String memberId,String memberPw) {
		Member member=new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		
		boolean isUpdated = memberService.updateMemberPw(member);
	
        if (isUpdated)
        {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
	}

	@PostMapping("/member_like")
	@ResponseBody
	public String userAddLike(@RequestBody Map<String, String> requestData, HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
		String cCode = requestData.get("cCode");
		MemberLike memberLike = memberService.memberLikeCheckFirst(cCode, memberId);
		if(memberLike.getLkState().equals("1")){
			memberService.updateMemberLikeStateOn(memberLike.getLkCode());
		}
		else{
			memberLike.setMemberId(memberId);
			memberLike.setCCode(cCode);
			memberService.memberAddLike(memberLike);
		}
		return "redirect:/map/map_company_info";
	}

	@GetMapping("/member_mypage_like_setting")
	public String LikeList(Model model, HttpSession session, RedirectAttributes redirectAttributes){
		String memberId = (String) session.getAttribute("SID");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}
		List<MemberLike> comLikes = memberService.memberGetLikeCompany(memberId);
		log.info("comLikes: {}", comLikes);
		model.addAttribute("comLikes",comLikes);
		return "member/member_mypage_like_setting";

	}

	@PostMapping("/updateAlarm")
	@ResponseBody
	public String updateMemberAlarm(@RequestParam("lkCode") String lkCode,
									@RequestParam("currentAlarmState") int lkAlarm) {
		memberService.updateMemberLikeAlarm(lkCode,lkAlarm);
		return "redirect:/map/map_company_info";
	}

	// 마이 페이지에서 구독 취소
	@PostMapping("/updateLikeState")
	@ResponseBody
	public String updateMemberLikeState(@RequestParam("lkCode") String lkCode) {
		memberService.updateMemberLikeState(lkCode);
		return "redirect:/map/map_company_info";
	}

	// 업체 페이지에서 구독 취소
	@PostMapping("/updateLikeStateForComInfo")
	@ResponseBody
	public String updateLikeState(@RequestBody Map<String, String> request) {
		String lkCode = request.get("lkCode");
		System.out.println("lkCode: " + lkCode);
		memberService.updateMemberLikeState(lkCode);
		return "redirect:/map/map_company_info";
	}


}
