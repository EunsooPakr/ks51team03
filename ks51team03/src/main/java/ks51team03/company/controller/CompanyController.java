package ks51team03.company.controller;
import jakarta.servlet.http.HttpSession;
import ks51team03.company.dto.*;
import ks51team03.company.service.CompanyService;
import ks51team03.files.dto.FileRequest;
import ks51team03.files.util.FileUtils;
import ks51team03.member.dto.Member;
import ks51team03.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

import ks51team03.company.dto.ComStaff;
import ks51team03.company.dto.Company;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

	private final MemberService memberService;
	private final CompanyService companyService;


	@GetMapping("/company/company_info")
	public String companyInfo(HttpSession session, Model model, RedirectAttributes redirectAttributes) {

		String memberId = (String) session.getAttribute("SID");
		String cCode = (String) session.getAttribute("CCODE");
		// 세션 아이디로 직원 테이블에서 업체 코드 찾기
		String companyCode = companyService.getCompanyCodeByMemberId(memberId);
		log.info("companyCode : {}", companyCode);

		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		log.info("memberLevel: {}", member.getMemberLevel());
		// 사용자 레벨 확인
		if (member == null || !member.getMemberLevel().equals("level2") && !member.getMemberLevel().equals("level3")) {
			redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
			return "redirect:/member/member_mypage_memberinfo"; // 에러 메시지를 보낼 페이지로 리다이렉트
		}

		// 직원일 경우
		if (companyCode != null) {
			// 업체 코드로 업체 정보 출력
			List<Company> companyInfoById = companyService.getCompanyInfoByCcode(companyCode);
			log.info("companyInfoById : {}", companyInfoById);
			// 현재 요일 가져오기
			List<ComOperTime> companyOperTime = companyService.getCompanyOperTime(companyCode);
			int companyReviewCount = companyService.getCompanyReviewCount(companyCode);
			DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
			String openingHours = getOpeningHoursForDay(dayOfWeek, companyOperTime);
			model.addAttribute("companyOperTime", companyOperTime);
			model.addAttribute("openingHours", openingHours);
			model.addAttribute("companyReviewCount", companyReviewCount);

			model.addAttribute("companyInfoById", companyInfoById);

		}
		// 업체 대표일 경우
		else {
			// 세션 아이디로 업체 정보 불러오기
			List<Company> companyInfoById = companyService.getCompanyInfoById(memberId);
			// 현재 요일 가져오기
			List<ComOperTime> companyOperTime = companyService.getCompanyOperTime(cCode);
			int companyReviewCount = companyService.getCompanyReviewCount(cCode);

			DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
			String openingHours = getOpeningHoursForDay(dayOfWeek, companyOperTime);
			model.addAttribute("companyOperTime", companyOperTime);
			model.addAttribute("openingHours", openingHours);
			model.addAttribute("companyReviewCount", companyReviewCount);
			log.info("companyInfoById : {}", companyInfoById);
			model.addAttribute("companyInfoById", companyInfoById);

		}
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
	public String companyModify(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		log.info("memberLevel: {}", member.getMemberLevel());
		// 사용자 레벨 확인
		if (member == null || !member.getMemberLevel().equals("level2") && !member.getMemberLevel().equals("level3")) {
			redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
			return "redirect:/member/member_mypage_memberinfo"; // 에러 메시지를 보낼 페이지로 리다이렉트
		}
		List<Company> companyListById = companyService.getCompanyInfoById(memberId);
		model.addAttribute("companyListById", companyListById);
		return "company/company_modify";
	}

	@PostMapping("/company/modify_Company")
	public String modifyCompany(Company company, HttpSession session) {
		String cCode = (String) session.getAttribute("CCODE");
		company.setCompanyCode(cCode);

		log.info("업체수정 company:{}", company);
		companyService.modifyCompany(company);
		return "redirect:/map/map_main";
	}

	@GetMapping("/company/company_staff_setting")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String companyStaffSetting(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");

		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		log.info("memberLevel: {}", member.getMemberLevel());
		// 사용자 레벨 확인
		if (member == null || !member.getMemberLevel().equals("level2")) {
			redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
			return "redirect:/member/member_mypage_memberinfo"; // 에러 메시지를 보낼 페이지로 리다이렉트
		}

		// 직원 등록 요청 목록을 가져와서 모델에 추가

		String cCode = (String) session.getAttribute("CCODE");
		List<ComStaff> staffRequests = companyService.getStaffSingList(cCode);
		List<ComStaff> staffList = companyService.getStaffList(cCode);

		model.addAttribute("staffRequests", staffRequests);
		model.addAttribute("staffList", staffList);

		return "company/company_staff_setting";
	}

	@PostMapping("/company/staff/delete")
	public String deleteStaff(@RequestParam String requestId, HttpSession session, RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		log.info("memberLevel: {}", member.getMemberLevel());
		// 사용자 레벨 확인
		if (member == null || !member.getMemberLevel().equals("level2")) {
			redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
			return "redirect:/member/member_mypage_memberinfo"; // 에러 메시지를 보낼 페이지로 리다이렉트
		}
		// 직원 해고 로직
		companyService.deleteStaff(requestId);
		return "redirect:/company/company_staff_setting";
	}

	@PostMapping("/company/staff/accept")
	public String acceptStaff(@RequestParam String requestId, HttpSession session, RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		log.info("memberLevel: {}", member.getMemberLevel());
		// 사용자 레벨 확인
		if (member == null || !member.getMemberLevel().equals("level2")) {
			redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
			return "redirect:/member/member_mypage_memberinfo"; // 에러 메시지를 보낼 페이지로 리다이렉트
		}
		// 직원 등록 요청 수락 로직
		companyService.acceptStaff(requestId,memberId);
		return "redirect:/company/company_staff_setting";
	}

	@PostMapping("/company/staff/reject")
	public String rejectStaff(@RequestParam String requestId, HttpSession session, RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		log.info("memberLevel: {}", member.getMemberLevel());
		// 사용자 레벨 확인
		if (member == null || !member.getMemberLevel().equals("level2")) {
			redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
			return "redirect:/member/member_mypage_memberinfo"; // 에러 메시지를 보낼 페이지로 리다이렉트
		}
		// 직원 등록 요청 거절 로직
		companyService.deleteStaff(requestId);
		return "redirect:/company/company_staff_setting";
	}

	// 직원 신청
	@GetMapping("/company/company_staff_signUp")
	public String companySignUp(Model model) {
		List<Company> companyList = companyService.getCompanyList();
		model.addAttribute("companyList", companyList);
		return "company/company_staff_signUp";
	}

	// 직원 신청 승인
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

		return "redirect:/member/member_mypage_main"; // 신청 후 리다이렉트
	}

	// 업체 문의 조회
	@GetMapping("/company/company_question")
	public String companyQuestion(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");
		String cCode = (String) session.getAttribute("CCODE");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		log.info("memberLevel: {}", member.getMemberLevel());
		// 사용자 레벨 확인
		if (member == null || !member.getMemberLevel().equals("level2") && !member.getMemberLevel().equals("level3")) {
			redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
			return "redirect:/member/member_mypage_memberinfo"; // 에러 메시지를 보낼 페이지로 리다이렉트
		}
		// 세션 아이디로 직원 테이블에서 업체 코드 찾기
		String companyCode = companyService.getCompanyCodeByMemberId(memberId);
		if (companyCode == null) {
			companyCode = cCode; // 업체 대표일 경우
		}

		if (companyCode != null) {
			List<ComQuestion> comQuestionList = companyService.getCompanyQuestion(companyCode);
			List<ComQuestionAnswer> comQuestionAnswers = companyService.getCompanyQuestionAnswer(companyCode);

			// 답변이 존재하는지 여부를 확인하여 각 문의에 설정
			Map<String, Boolean> questionAnswerMap = new HashMap<>();
			for (ComQuestion question : comQuestionList) {
				boolean hasAnswer = comQuestionAnswers.stream()
						.anyMatch(answer -> answer.getQuesNum().equals(question.getQuesNum()));
				questionAnswerMap.put(question.getQuesNum(), hasAnswer);
			}
			model.addAttribute("comQuestionList", comQuestionList);
			model.addAttribute("questionAnswerMap", questionAnswerMap);
			return "company/company_question";
		}

		// 접근할 수 없는 경우, 에러 페이지 또는 다른 페이지로 리다이렉트
		return "redirect:/access_denied";
	}

	// 문의 등록
	@PostMapping("/company/submit_question")
	public String submitQuestion(@ModelAttribute ComQuestion comQuestion, HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
		comQuestion.setMemberId(memberId);
		log.info("Received cCode: {}", comQuestion.getCCode());
		log.info("Received qcteNum: {}", comQuestion.getQcteNum());

		// qctcKind 값을 qctenum 값으로 변환
		String qctenum = getQctenum(comQuestion.getQcteNum(), comQuestion.getCCode());
		comQuestion.setQcteNum(qctenum);
		comQuestion.setQuesDate(LocalDate.now().toString());

		companyService.addQuestion(comQuestion);

		return "redirect:/map/map_main?success=true";
	}
	private String getQctenum(String qcteNum, String comClass) {
		Map<String, String> qctenumMap = new HashMap<>();
		qctenumMap.put("병원이용", "qctc1");
		qctenumMap.put("기타문의", "qctc11");
//		qctenumMap.put("기타문의", "qctc10");
//		qctenumMap.put("기타문의", "qctc12");
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

	// 리뷰 등록
	@PostMapping("/company/submit_review")
	public String submitReview(@RequestParam("cCode")String cCode, ComReview comReview, HttpSession session) {
		String memberId = (String) session.getAttribute("SID");
		comReview.setMemberId(memberId);
		comReview.setCCode(cCode);
		log.info("comReview: {}", comReview);
		companyService.addReviewWithFile(comReview);

		//companyService.addReview(comReview);
		return "redirect:/map/map_main";
	}

	//업체 문의 삭제
	@PostMapping("/company/company_question_delete")
	public String deleteQuestion(@RequestParam("quesNum") String quesNum) {
		companyService.deleteAnswersByQuesNum(quesNum);
		companyService.deleteQuestion(quesNum);
		return "redirect:company_question";
	}

	// 문의 답변
	@GetMapping("/company/company_question_answer")
	public String companyQuestionAnswer(@RequestParam("quesnum") String quesnum, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		log.info("memberLevel: {}", member.getMemberLevel());
		// 사용자 레벨 확인
		if (member == null || !member.getMemberLevel().equals("level2") && !member.getMemberLevel().equals("level3")) {
			redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
			return "redirect:/member/member_mypage_memberinfo"; // 에러 메시지를 보낼 페이지로 리다이렉트
		}
		ComQuestion question = companyService.getCompanyQuestionById(quesnum);
		model.addAttribute("question", question);
		return "company/company_question_answer";
	}

	@PostMapping("/company/submit_answer")
	public String submitAnswer(ComQuestionAnswer comQuestionAnswer, HttpSession session) {
		String memberId = (String)session.getAttribute("SID");
		comQuestionAnswer.setMemberId(memberId);
		companyService.addAnswer(comQuestionAnswer);
		return "redirect:/company/company_question";
	}

	// 문의 답변 수정
	@GetMapping("/company/company_question_answer_modify")
	public String companyQuestionAnswerModify(@RequestParam("quesnum") String quesnum, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		String memberId = (String)session.getAttribute("SID");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		log.info("memberLevel: {}", member.getMemberLevel());
		// 사용자 레벨 확인
		if (member == null || !member.getMemberLevel().equals("level2") && !member.getMemberLevel().equals("level3")) {
			redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
			return "redirect:/member/member_mypage_memberinfo"; // 에러 메시지를 보낼 페이지로 리다이렉트
		}
		ComQuestion question = companyService.getCompanyQuestionById(quesnum);
		ComQuestionAnswer answer = companyService.getAnswerByQuesNum(quesnum);
		log.info("answer: {}", answer);
		model.addAttribute("question", question);
		model.addAttribute("answer", answer);
		return "company/company_question_answer_modify";
	}

	// 문의 답변 수정 post
	@PostMapping("/company/update_answer")
	public String updateAnswer(ComQuestionAnswer comQuestionAnswer, HttpSession session) {
		// 세션에서 memberId를 가져옴
		String memberId = (String) session.getAttribute("SID");
		comQuestionAnswer.setMemberId(memberId);

		// 답변 수정
		companyService.updateAnswer(comQuestionAnswer);

		return "redirect:/company/company_question";
	}

	@PostMapping("/company/review_delete")
	public String deleteReview(@RequestParam("revCode")String revCode){
		log.info("revCode: {}", revCode);
		companyService.deleteReview(revCode);
		return "redirect:company_review";
	}

	@GetMapping("/company/company_review")
	public String companyReview(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");
		String cCode = (String) session.getAttribute("CCODE");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}

		// 사용자 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		log.info("memberLevel: {}", member.getMemberLevel());
		// 사용자 레벨 확인
		if (member == null || !member.getMemberLevel().equals("level2") && !member.getMemberLevel().equals("level3")) {
			redirectAttributes.addFlashAttribute("errorMessage", "접근 권한이 없습니다.");
			return "redirect:/member/member_mypage_memberinfo"; // 에러 메시지를 보낼 페이지로 리다이렉트
		}
		// 세션 아이디로 직원 테이블에서 업체 코드 찾기
		String companyCode = companyService.getCompanyCodeByMemberId(memberId);
		if (companyCode == null) {
			companyCode = cCode; // 업체 대표일 경우
		}
		if (companyCode != null) {
			List<ComReview> comReviews = companyService.getCompanyReview(companyCode);
			log.info("comReviews: {}", comReviews);
			model.addAttribute("comReviews", comReviews);

		}

		return "company/company_review";
	}


	@GetMapping("/company/company_send_alarm")
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
