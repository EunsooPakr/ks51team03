package ks51team03.map.controller;

import ks51team03.company.dto.Company;
import ks51team03.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MapController {
	private final CompanyService companyService;

	@GetMapping("/map/map_main")
	public String mapMainPage(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
		List<Company> companyList = companyService.getCompanyList(keyword);
		log.info("companyList: {}", companyList);
		log.info("keyword: {}", keyword);
		model.addAttribute("companyList", companyList);
		return "map/map_main";
	}

	@ResponseBody
	@GetMapping("/map/get_company_list")
	public List<Company> getCompanyList(@RequestParam(value = "keyword", required = false) String keyword) {
		return companyService.getCompanyList(keyword);
	}

	@GetMapping("/map/map_write_question")
	public String writeQuestion() {
		return "map/map_write_question";
	}

	@GetMapping("/map/map_write_review")
	public String writeReview() {
		return "map/map_write_review";
	}

	@GetMapping("/map/map_company_info")
	public String companyInfo() {
		return "map/map_company_info";
	}
}