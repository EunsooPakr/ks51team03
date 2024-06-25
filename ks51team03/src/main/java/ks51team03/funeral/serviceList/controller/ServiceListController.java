package ks51team03.funeral.serviceList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceListController {

	@GetMapping("/funeral/serviceList")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String funeralServiceList() {

		return "funeral/funeral_serviceList";
	}
}
