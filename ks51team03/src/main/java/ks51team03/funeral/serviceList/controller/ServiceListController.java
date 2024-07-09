package ks51team03.funeral.serviceList.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.service.ReserveService;
import ks51team03.funeral.serviceList.dto.ServiceListDto;
import ks51team03.funeral.serviceList.mapper.ServiceListMapper;
import ks51team03.funeral.serviceList.service.ServiceListService;
import ks51team03.member.dto.Member;
import ks51team03.member.service.MemberServiceImpl;
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
public class ServiceListController {


	private final ServiceListService serviceListService;
	private final ReserveService reserveService;
	private final MemberServiceImpl memberService;

	@PostMapping("/funeral/funeral_service_detail")
	public String funeralReserve(HttpSession session, Model model, ReserveDto reserveDto) {
		String memberId = (String) session.getAttribute("SID");

		log.info("html에서 포스트맵핑되었습니다.");

		log.info("로그인한 회원 아이디 memberId={}", memberId);
		log.info("reserveDto:{}", reserveDto);

		// 회원 정보 조회
		Member member = memberService.getMemberInfoById(memberId);
		String reservePhone = member.getMemberPhone();

		log.info("과연 어디서 막히는 지 보자111");
		// 새로운 frcode 생성하기
		String getLastFuneralServiceCode = reserveService.getLastFuneralServiceCode();

		log.info("과연 어디서 막히는 지 보자222");


		reserveDto.setReserveCode(getLastFuneralServiceCode);
		reserveDto.setReserveId(memberId);
		reserveDto.setReservePhone(reservePhone);

		log.info("과연 어디서 막히는 지 보자3333");
		reserveService.funeralReserve(reserveDto);
		//List<ReserveDto> reserveDto = reserveService.funeralReserve(new ReserveDto());

		log.info("reserveDto={}", reserveDto);
		model.addAttribute("reserveDto", reserveDto);

		log.info("과연 어디서 막히는 지 보자444");

		return "redirect:/funeral/funeral_reserve_info";
	}



	@GetMapping("/funeral/serviceList")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String funeralServiceList(Model model, HttpSession session) {

		String memberId = (String) session.getAttribute("SID");

		log.info("로그인한 회원 아이디 memberId={}", memberId);

		List<ServiceListDto> serviceListDto = serviceListService.getServiceList();

		model.addAttribute("title", "장례 전체 서비스");
		model.addAttribute("serviceListDto", serviceListDto);

		return "funeral/funeral_serviceList";
	}

	@GetMapping("/funeral/funeral_total_serviceList")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String funeralTotalServiceList(Model model, HttpSession session) {

		String memberId = (String) session.getAttribute("SID");

		log.info("로그인한 회원 아이디 memberId={}", memberId);

		List<ServiceListDto> serviceListDto = serviceListService.getServiceList();

		model.addAttribute("title", "장례 전체 서비스");
		model.addAttribute("serviceListDto", serviceListDto);

		return "funeral/funeral_total_serviceList";
	}

	@GetMapping("funeral/funeral_service_detail")
	public String funeralReserve(
			@RequestParam(value="funeralserviceCcode") String funeralserviceCcode,
			Model model, HttpSession session) {
		log.info("funeralserviceCcode: ?????" + funeralserviceCcode);

		String memberId = (String) session.getAttribute("SID");

		log.info("로그인한 회원 아이디 memberId={}", memberId);

		List<ServiceListDto> serviceListCcode = serviceListService.getServiceInfoByCode(funeralserviceCcode);
		//ServiceListDto serviceListCcode = serviceListService.getServiceInfoByCode(funeralserviceCcode);
		//List<ServiceListDto> serviceListDto = serviceListService.getServiceList();

		model.addAttribute("reserveCompanyCode", funeralserviceCcode);
		//model.addAttribute("serviceListDto", serviceListDto);
		model.addAttribute("serviceListCcode", serviceListCcode);

		return "funeral/funeral_service_detail";
	}


}
