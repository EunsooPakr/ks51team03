package ks51team03.map.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ks51team03.company.dto.ComMap;
import ks51team03.company.dto.Company;
import ks51team03.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MapController {
	private final CompanyService companyService;


	@GetMapping("/map/map_main")
	public String mapMainPage(Model model, @RequestParam(value = "keyword", required = false) String keyword) throws JsonProcessingException {
		List<Company> companyList = companyService.getCompanyList(keyword);
		List<ComMap> comMapList = new ArrayList<>();

		// 회사 목록에서 cCode 값을 추출하여 ComMap 객체를 가져옵니다
		for (Company company : companyList) {
			ComMap comMap = companyService.getComMapByCCode(company.getCompanyCode());
			if (comMap != null) {
				comMapList.add(comMap);
			}
		}

		log.info("keyword: {}", keyword);
		log.info("comMapList: {}", comMapList); // comMapList 확인 로그

		ObjectMapper objectMapper = new ObjectMapper();
		String comMapListJson = objectMapper.writeValueAsString(comMapList);

		model.addAttribute("companyList", companyList);
		model.addAttribute("comMapListJson", comMapListJson); // 모델에 추가

		return "map/map_main";
	}

	@ResponseBody
	@GetMapping("/map/get_company_list")
	public List<Company> getCompanyList(@RequestParam(value = "keyword", required = false) String keyword) {
		log.info("Received keyword: {}", keyword);
		List<Company> companyList = companyService.getCompanyList(keyword);
		log.info("Resulting company list: {}", companyList);
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
				log.info("comMapList: {}", comMapList);

			}
		}
		return comMapList;
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