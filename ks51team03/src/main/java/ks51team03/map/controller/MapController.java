package ks51team03.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {
	
	@GetMapping("/map/map_main")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String mapMainPage() {

		return "map/map_main";
	}

	@GetMapping("/map/map_write_question")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String writeQuestion() {

		return "map/map_write_question";
	}
	@GetMapping("/map/map_write_review")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String writeReview() {

		return "map/map_write_review";
	}
	@GetMapping("/map/map_company_info")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String companyInfo() {

		return "map/map_company_info";
	}

}
