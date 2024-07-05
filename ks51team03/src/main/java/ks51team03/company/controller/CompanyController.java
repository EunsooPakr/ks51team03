package ks51team03.company.controller;
import jakarta.servlet.http.HttpSession;
import ks51team03.company.dto.*;
import ks51team03.company.service.CompanyService;
import ks51team03.member.dto.Member;
import ks51team03.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.DayOfWeek;
import java.time.LocalDate;
import ks51team03.company.dto.ComStaff;
import ks51team03.company.dto.Company;



@Controller
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

	private final MemberService memberService;
	private final CompanyService companyService;

	@GetMapping("/company/company_info")
	public String companyInfo(HttpSession session, Model model) {

		String memberId = (String) session.getAttribute("SID");
		String ccode = (String) session.getAttribute("CCODE");
		List<Company> companyListById = companyService.getCompanyListById(memberId);
		List<ComOperTime> companyOperTime = companyService.getCompanyOperTime(ccode);
		int companyReviewCount = companyService.getCompanyReviewCount(ccode);
		log.info("ReviewCount: {}", companyReviewCount);
		// 현재 요일 가져오기
		DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
		String openingHours = getOpeningHoursForDay(dayOfWeek, companyOperTime);

		model.addAttribute("companyListById", companyListById);
		model.addAttribute("companyOperTime", companyOperTime);
		model.addAttribute("openingHours", openingHours);
		model.addAttribute("companyReviewCount", companyReviewCount);

		return "company/company_info";
	}
	private String getOpeningHoursForDay(DayOfWeek dayOfWeek, List<ComOperTime> companyOperTime) {
		if (companyOperTime.isEmpty()) {
			return "정보 없음";
		}

		ComOperTime operTime = companyOperTime.get(0); // assuming one entry for simplicity
		switch (dayOfWeek) {
			case MONDAY:
				return operTime.getOtMon();
			case TUESDAY:
				return operTime.getOtTue();
			case WEDNESDAY:
				return operTime.getOtWed();
			case THURSDAY:
				return operTime.getOtThu();
			case FRIDAY:
				return operTime.getOtFri();
			case SATURDAY:
				return operTime.getOtSat();
			case SUNDAY:
				return operTime.getOtSun();
			default:
				return "휴무";
		}
	}
	@GetMapping("/company/company_modify")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String companyModify(Model model, HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
		List<Company> companyListById = companyService.getCompanyListById(memberId);
		model.addAttribute("companyListById", companyListById);
		return "company/company_modify";
	}

	@PostMapping("/company/modify_Company")
	public String modifyCompany(Company company, HttpSession session) {
		String ccode = (String) session.getAttribute("CCODE");
		company.setCompanyCode(ccode);

		log.info("업체수정 company:{}", company);
		companyService.modifyCompany(company);
		return "redirect:/company/company_info";
	}

	@GetMapping("/company/company_staff_setting")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String companyStaffSetting(Model model, HttpSession session) {
		// 직원 등록 요청 목록을 가져와서 모델에 추가

		String ccode = (String) session.getAttribute("CCODE");
		List<ComStaff> staffRequests = companyService.getStaffSingList(ccode);
		List<ComStaff> staffList = companyService.getStaffList(ccode);

		model.addAttribute("staffRequests", staffRequests);
		model.addAttribute("staffList", staffList);

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
	public String companySignUp(Model model) {
		List<Company> companyList = companyService.getCompanyList();
		model.addAttribute("companyList", companyList);
		return "company/company_staff_signUp";
	}
	@PostMapping("/company/staff/sign")
	public String signStaff(@RequestParam("companyCode") String companyCode, HttpSession session) {
		String memberId = (String) session.getAttribute("SID");

		// 회원 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		String phone = member.getMemberPhone();

		// 새로운 stfcode 생성 (가장 높은 숫자 찾아 1 더하기)
		String newStfCode = companyService.getNewStfCode();

		ComStaff comStaff = new ComStaff();
		comStaff.setStfCode(newStfCode);
		comStaff.setMemberId(memberId);
		comStaff.setCCode(companyCode);
		comStaff.setLevel("level3");
		comStaff.setStfPhone(phone);
		comStaff.setStfCheck("0");
		comStaff.setStfApproId("");
		comStaff.setStfDate(null);

		companyService.insertStaff(comStaff);

		return "redirect:/company/company_info"; // 신청 후 리다이렉트
	}

	@GetMapping("/company/company_question")
	public String companyQuestion(Model model, HttpSession session) {
		String ccode = (String) session.getAttribute("CCODE");
		List<ComQuestion> comQuestionList = companyService.getCompanyQuestion(ccode);
		model.addAttribute("comQuestionList", comQuestionList);
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
