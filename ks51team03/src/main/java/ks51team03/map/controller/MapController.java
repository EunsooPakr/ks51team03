package ks51team03.map.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import ks51team03.company.dto.*;
import ks51team03.company.service.CompanyService;
import ks51team03.member.dto.Member;
import ks51team03.member.dto.MemberLike;
import ks51team03.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MapController {
	private final CompanyService companyService;
	private final MemberServiceImpl memberService;


	@GetMapping("/map/map_main")
	public String mapMainPage() {


		return "map/map_main";
	}

	@ResponseBody
	@GetMapping("/map/get_company_list")
	public List<Company> getCompanyList(@RequestParam(value = "keyword", required = false) String keyword) {
		List<Company> companyList = companyService.getCompanyListByKeyWord(keyword);
		return companyList;
	}

	@PostMapping("/map/get_com_map_list")
	@ResponseBody
	public List<ComMap> getComMapList(@RequestBody List<String> cCodes) {
		List<ComMap> comMapList = new ArrayList<>();
		for (String companyCode : cCodes) {
			ComMap comMap = companyService.getComMapByCCode(companyCode);
			if (comMap != null) {
				comMapList.add(comMap);

			}
		}

		return comMapList;
	}

	@ResponseBody
	@GetMapping("/map/getCompanyInfo")
	public Map<String, Object> getCompanyInfo(@RequestParam("cCode") String cCode) {
		Map<String, Object> response = new HashMap<>();
		int reviewCount = companyService.getCompanyReviewCount(cCode);
		double avgReviewScore = companyService.getAvgReviewScore(cCode);
		List<String> comImg = companyService.getCompanyImgByCcodeForMap(cCode);
		List<ComReview> reviews = companyService.getCompanyReview(cCode);
		if(reviews != null) {
			Member revMember = memberService.getMemberInfoById(reviews.get(0).getMemberId());
			response.put("revMember", revMember);
			response.put("reviews", reviews);
		}

		response.put("reviewCount", reviewCount);
		response.put("avgReviewScore", avgReviewScore);
		response.put("comImg", comImg);

		return response;
	}

	@GetMapping("/map/map_write_question")
	public String writeQuestion(@RequestParam("cCode") String cCode, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}
		model.addAttribute("cCode", cCode);
		return "map/map_write_question";
	}

	@GetMapping("/map/map_write_review")
	public String writeReview(@RequestParam("cCode") String cCode, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");
		// 세션에 아이디가 없으면 로그인 페이지로 리다이렉트
		if (memberId == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "로그인을 하는게 좋을거같은데");
			return "redirect:/map/map_main"; // 로그인 페이지로 리다이렉트
		}
		model.addAttribute("cCode", cCode);
		return "map/map_write_review";
	}

	@GetMapping("/map/map_company_info")
	public String companyInfo(@RequestParam("cCode") String cCode, Model model, HttpSession session) {
		String memberId = (String)session.getAttribute("SID");
		// 업체 코드로 업체 정보 출력
		List<Company> companyInfoById = companyService.getCompanyInfoByCcode(cCode);
		// 현재 요일 가져오기
		List<ComOperTime> companyOperTime = companyService.getCompanyOperTime(cCode);
		int companyReviewCount = companyService.getCompanyReviewCount(cCode);
		List<CompanyImg> companyImgs = companyService.getCompanyImgByCcode(cCode);
		DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
		String openingHours = getOpeningHoursForDay(dayOfWeek, companyOperTime);
		MemberLike likeCheck = memberService.memberLikeCheck(cCode, memberId);
		if(likeCheck != null){
			model.addAttribute("likeCheck", likeCheck.getLkState());
			model.addAttribute("lkCode", likeCheck.getLkCode());
		}
		model.addAttribute("companyOperTime", companyOperTime);
		model.addAttribute("openingHours", openingHours);
		model.addAttribute("companyReviewCount", companyReviewCount);
		model.addAttribute("companyImgs", companyImgs);
		model.addAttribute("companyInfoById", companyInfoById);

		return "map/map_company_info";
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

	@GetMapping("/map/map_myQandR")
	public String myQandR() {
		return "map/map_myQandR";
	}
}