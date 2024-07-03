package ks51team03.company.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ks51team03.company.dto.ComStaff;
import ks51team03.company.dto.Company;
import ks51team03.company.service.CompanyService;
import ks51team03.member.dto.MemberLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CompanyController {
	private final CompanyService companyService;

	@GetMapping("/company/company_info")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String companyInfo() {
		//완
		return "company/company_info";
	}
	@GetMapping("/company/company_modify")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String companyModify() {
		//완
		return "company/company_modify";
	}

	@GetMapping("/company/company_staff_setting")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String companyStaffSetting(Model model) {
		// 직원 등록 요청 목록을 가져와서 모델에 추가
		//완
		List<ComStaff> staffRequests;
		//model.addAttribute("staffRequests", staffRequests);
		return "company/company_staff_setting";
	}
	@PostMapping("/staff/accept")
	public String acceptStaff(@RequestParam Long requestId) {
		// 직원 등록 요청 수락 로직
		return "redirect:/company/staff/setting";
	}

	@PostMapping("/staff/reject")
	public String rejectStaff(@RequestParam Long requestId) {
		// 직원 등록 요청 거절 로직
		return "redirect:/company/staff/setting";
	}

	@GetMapping("/company/company_staff_signUp")
	public String companySignUp() {
		//완
		return "company/company_staff_signUp";
	}

	@GetMapping("/company/company_question")
	public String companyQuestion() {
		//완
		return "company/company_question";
	}

	@GetMapping("/company/company_question_answer")
	public String companyQuestionAnswer() {
		//완
		return "company/company_question_answer";
	}

	@GetMapping("/company/company_review")
	public String companyReview() {
		//완
		return "company/company_review";
	}


	@GetMapping("/company/company_send_alarm")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String companySendAlarm() {

		return "company/company_send_alarm";
	}
	
	/*업체 등록*/
	@PostMapping("/company/insertCompany")
	public String insertCompany(Company company) {
		
		log.info("업체등록 Company:{}", company);
		
		companyService.insertCompany(company);
		
		return "redirect:/member/member_main";
	}
	
	@GetMapping("/company/insertCompany")
	public String insertCompany(Model model) {
		
		model.addAttribute("title", "업체등록");
		
		return "member/member_login_insert_com";
	}

}
