package ks51team03.company.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.company.dto.ComOperTime;
import ks51team03.company.dto.ComStaff;
import ks51team03.company.dto.Company;
import ks51team03.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CompanyController {
	private final CompanyService companyService;

	@GetMapping("/company/company_info")
	public String companyInfo(HttpSession session, Model model) {

		String memberId = (String) session.getAttribute("SID");
		String ccode = (String) session.getAttribute("CCODE");
		List<Company> companyList = companyService.getCompanyList(memberId);
		List<ComOperTime> companyOperTime = companyService.getCompanyOperTime(ccode);
		model.addAttribute("companyList", companyList);
		model.addAttribute("companyOperTime", companyOperTime);

		return "company/company_info";
	}
	@GetMapping("/company/company_modify")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String companyModify() {

		return "company/company_modify";
	}

	@GetMapping("/company/company_staff_setting")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String companyStaffSetting(Model model, HttpSession session) {
		// 직원 등록 요청 목록을 가져와서 모델에 추가

		String ccode = (String) session.getAttribute("CCODE");
		List<ComStaff> staffRequests = companyService.getStaffSingList(ccode);

		model.addAttribute("staffRequests", staffRequests);
		return "company/company_staff_setting";
	}
	@PostMapping("/company/staff/accept")
	public String acceptStaff(@RequestParam String requestId, HttpSession session) {
		// 직원 등록 요청 수락 로직
		String memberId = (String) session.getAttribute("SID");
		companyService.acceptStaff(requestId,memberId);
		return "redirect:/company/company_staff_setting";
	}

	@PostMapping("/staff/reject")
	public String rejectStaff(@RequestParam String requestId) {
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

}
