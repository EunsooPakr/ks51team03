package ks51team03.funeral.serviceList.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.company.dto.Company;
import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.dto.ReserveMemberPet;
import ks51team03.funeral.reserve.service.ReserveService;
import ks51team03.funeral.serviceList.dto.ServiceImgDto;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
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

		log.info("선택한 반려동물 이름: {}", reserveDto.getReservePetName());

		reserveDto.setReserveId(memberId);
		reserveDto.setReservePhone(reservePhone);

		reserveService.funeralReserve(reserveDto);
		log.info("reserveDto={}", reserveDto);
		model.addAttribute("reserveDto", reserveDto);

		return "redirect:/funeral/funeral_reserve_info";
	}




	@GetMapping("/funeral/serviceList")			// 어노테이션 괄호안에는 옵션을 쓴다.   /  컨트롤러에서는 무조건 String으로 반환
	public String funeralServiceList(Model model, HttpSession session) {

		String memberId = (String) session.getAttribute("SID");

		log.info("로그인한 회원 아이디 memberId={}", memberId);

		List<Company> ComServiceListDto = serviceListService.getCompanyInfoList();

		log.info("ComServiceListDto:{}", ComServiceListDto);

		model.addAttribute("title", "장례 전체 서비스");
		model.addAttribute("ComServiceListDto", ComServiceListDto);

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
		log.info("funeralserviceCcode: " + funeralserviceCcode);

		String memberId = (String) session.getAttribute("SID");
		log.info("로그인한 회원 아이디 memberId={}", memberId);

		List<ServiceListDto> serviceListCcode = serviceListService.getServiceInfoByCode(funeralserviceCcode);

		// memberId를 전달
		List<ReserveMemberPet> getMemberPetList = serviceListService.getMemberPet(memberId);
		log.info("getMemberPetList:{}", getMemberPetList);

		model.addAttribute("reserveCompanyCode", funeralserviceCcode);
		model.addAttribute("serviceListCcode", serviceListCcode);
		model.addAttribute("getMemberPetList", getMemberPetList);

		return "funeral/funeral_service_detail";
	}


	@GetMapping("funeral/funeral_insert_service")
	public String funeralInsertService(HttpSession session, Company company, Model model){
		String memberId = (String) session.getAttribute("SID");

		log.info("로그인한 회원 아이디 memberId={}", memberId);

		if(memberId == null) {
			return "redirect:/";
		}

		company.setMemberId(memberId);

		List<Company> getCompanyInfoList = serviceListService.getCompanyInfo(company);

		if (getCompanyInfoList == null || getCompanyInfoList.isEmpty()) {
			getCompanyInfoList = new ArrayList<>();
		} else {
			// ccode를 세션에 저장
			String ccode = getCompanyInfoList.get(0).getCompanyCode();
			session.setAttribute("ccode", ccode);
		}

		log.info("getCompanyInfoList:{}", getCompanyInfoList);
		model.addAttribute("getCompanyInfoList", getCompanyInfoList);

		return "funeral/funeral_insert_service";
	}


	@PostMapping("funeral/funeral_insert_service")
	public String funeralInsertService(@ModelAttribute ServiceListDto serviceListDto,
									   @RequestParam("furImgFile") MultipartFile multipartFile,
									   HttpSession session, Model model,
									   ServiceImgDto serviceImgDto,
									   RedirectAttributes redirectAttributes) {
		String memberId = (String) session.getAttribute("SID");
		serviceListDto.setFuneralserviceId(memberId);

		String ccode = (String) session.getAttribute("ccode");
		String fscode = serviceListService.insertFuneralService(serviceListDto);

		serviceImgDto.setFurImgFile(multipartFile);
		serviceImgDto.setCcode(ccode);
		serviceImgDto.setFscode(fscode);
		serviceListService.addFuneralServiceImg(serviceImgDto, ccode);
		if (ccode == null) {

			throw new IllegalStateException("ccode should not be null");
		}

//        serviceListDto.setFuneralserviceCcode(ccode);
//
//
//        log.info("fscode: {}", fscode);
//        ServiceImgDto serviceImgDto = new ServiceImgDto();
//        serviceImgDto.setFurImgFile(multipartFile);
//        serviceImgDto.setCcode(ccode);
//        serviceImgDto.setFscode(fscode);
//
//        serviceListService.insertFuneralService(serviceListDto);
//        serviceListService.addFuneralServiceImg(serviceImgDto, serviceListDto.getFuneralserviceCcode()); //이미지 넣기
//
//
//        model.addAttribute("serviceListDto", serviceListDto);
//        model.addAttribute("ccode", ccode);
//        model.addAttribute("serviceImgDto", serviceImgDto);

		// 메시지 설정
		redirectAttributes.addFlashAttribute("message", "장례 서비스가 정상적으로 등록되었습니다.");

		return "redirect:/funeral/funeral_insertService_confirm";
	}


	@GetMapping("funeral/funeral_insertService_confirm")
	public String serviceConfirm(HttpSession session, Model model, RedirectAttributes redirectAttributes, ServiceListDto serviceListDto) {
		String memberId = (String) session.getAttribute("SID");
		serviceListDto.setFuneralserviceId(memberId);

		model.addAttribute("serviceListDto", serviceListDto);

		return "funeral/funeral_insertService_confirm";
	}

	@GetMapping("funeral/funeral_regService_List")
	public String getRegServiceList(Model model, HttpSession session, ServiceListDto serviceListDto) {
		String memberId = (String) session.getAttribute("SID");

		log.info("로그인한 회원 아이디 memberId={}", memberId);

		serviceListDto.setFuneralserviceId(memberId);

		if(memberId == null) {
			return "redirect:/";
		}

		List<ServiceListDto> serviceList = serviceListService.getServiceList(serviceListDto);

		log.info("getRegServiceList:{}", serviceList );

		model.addAttribute("serviceListDto", serviceListDto);
		model.addAttribute("serviceList", serviceList);

		return "funeral/funeral_regService_List";
	}

	@GetMapping("funeral/funeral_company_service_modify")
	public String modifyFuneralService(Model model, HttpSession session, ServiceListDto serviceListDto, Company company,
									   @RequestParam(value="funeralserviceCode") String funeralserviceCode) {
		String memberId = (String) session.getAttribute("SID");

		log.info("로그인한 회원 아이디 memberId={}", memberId);

		if(memberId == null) {
			return "redirect:/";
		}

		company.setMemberId(memberId);

		List<ServiceListDto> serviceListCode = serviceListService.modifyServiceInfoByCode(funeralserviceCode);

		List<Company> getCompanyInfoList = serviceListService.getCompanyInfo(company);

		if (getCompanyInfoList == null || getCompanyInfoList.isEmpty()) {
			getCompanyInfoList = new ArrayList<>();
		} else {
			// ccode를 세션에 저장
			String ccode = getCompanyInfoList.get(0).getCompanyCode();
			session.setAttribute("ccode", ccode);
		}

		log.info("getCompanyInfoList:{}", getCompanyInfoList);

		log.info("serviceListCode:{}", serviceListCode);

		model.addAttribute("serviceListDto", serviceListDto);
		model.addAttribute("serviceListCode", serviceListCode);
		model.addAttribute("getCompanyInfoList", getCompanyInfoList);


		return "funeral/funeral_company_service_modify.html";
	}

	@PostMapping("funeral/funeral_company_service_modify")
	public String modifyFuneralService(Model model,HttpSession session, ServiceListDto serviceListDto){
		String memberId = (String) session.getAttribute("SID");
		serviceListDto.setFuneralserviceId(memberId);

		log.info("로그인한 회원 아이디 memberId={}", memberId);

		if (memberId == null) {
			return "redirect:/";
		}

		serviceListDto.setFuneralserviceId(memberId);

		serviceListService.updateServiceInfo(serviceListDto);

		return "redirect:/funeral/funeral_regService_List";
	}

}
