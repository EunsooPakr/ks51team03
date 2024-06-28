package ks51team03.funeral.serviceList.controller;

import ks51team03.funeral.serviceList.dto.ServiceListDto;
import ks51team03.funeral.serviceList.mapper.ServiceListMapper;
import ks51team03.funeral.serviceList.service.ServiceListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ServiceListController {


	private final ServiceListService serviceListService;

	@GetMapping("/funeral/serviceList")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String funeralServiceList(Model model) {

		List<ServiceListDto> serviceListDto = serviceListService.getServiceList();

		model.addAttribute("title", "장례 전체 서비스");
		model.addAttribute("serviceListDto", serviceListDto);

		return "funeral/funeral_serviceList";
	}

	@GetMapping("/funeral/funeral_total_serviceList")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String funeralTotalServiceList(Model model) {

		List<ServiceListDto> serviceListDto = serviceListService.getServiceList();

		model.addAttribute("title", "장례 전체 서비스");
		model.addAttribute("serviceListDto", serviceListDto);

		return "funeral/funeral_total_serviceList";
	}



}
