package ks51team03.funeral.reserve.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.dto.ReserveInfoDto;
import ks51team03.funeral.reserve.dto.ReserveServiceInfoDto;
import ks51team03.funeral.reserve.service.ReserveService;
import ks51team03.funeral.serviceList.service.ServiceListService;
import ks51team03.member.dto.Member;
import ks51team03.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReserveController {

    private final ServiceListService serviceListService;
    private final ReserveService reserveService;
    private final MemberServiceImpl memberService;

    @PostMapping("funeral/funeral_reserve_payment")
    public ResponseEntity<String> reservePayment(){

        return ResponseEntity.ok("Success");

    }



    @GetMapping("funeral/funeral_reserve_info")
    public String reserveInfo(Model model, HttpSession session, ReserveInfoDto reserveInfoDto, ReserveServiceInfoDto reserveServiceInfoDto) {
            String memberId = (String) session.getAttribute("SID");

        log.info("장례 예약한 회원 아이디 memberId = {}", memberId);

        log.info("여기는 멤버아이디 나오는 곳");

        //회원 정보 조회
        Member member = memberService.getMemberInfoById(memberId);
        String reservePhone = member.getMemberPhone();

        reserveInfoDto.setReserveId(memberId);
        reserveInfoDto.setReservePhone(reservePhone);

        reserveServiceInfoDto.setReserveId(memberId);

        List<ReserveInfoDto> reserveInfoList = reserveService.funeralReserveInfo(reserveInfoDto);
        List<ReserveServiceInfoDto> reserveServiceInfoDtoList = reserveService.funeralReserveServiceInfo(reserveServiceInfoDto);

        log.info("reserveInfoList = {}", reserveInfoList);
        log.info("reserveServiceInfoDtoList = {}", reserveServiceInfoDtoList);
        model.addAttribute("reserveInfoList", reserveInfoList);
        model.addAttribute("reserveServiceInfoDtoList", reserveServiceInfoDtoList);

        return "funeral/funeral_reserve_info";
    }

    @GetMapping("funeral/funeral_reserve_payment")
    public String reservePayment(Model model, HttpSession session, ReserveInfoDto reserveInfoDto, ReserveServiceInfoDto reserveServiceInfoDto) {

        String memberId = (String) session.getAttribute("SID");

        log.info("장례 예약한 회원 아이디 memberId = {}", memberId);

        log.info("여기는 멤버아이디 나오는 곳");

        //회원 정보 조회
        Member member = memberService.getMemberInfoById(memberId);
        String reservePhone = member.getMemberPhone();

        reserveInfoDto.setReserveId(memberId);
        reserveInfoDto.setReservePhone(reservePhone);

        reserveServiceInfoDto.setReserveId(memberId);

        List<ReserveInfoDto> reserveInfoList = reserveService.funeralReserveInfo(reserveInfoDto);
        List<ReserveServiceInfoDto> reserveServiceInfoDtoList = reserveService.funeralReserveServiceInfo(reserveServiceInfoDto);

        log.info("reserveInfoList = {}", reserveInfoList);
        log.info("reserveServiceInfoDtoList = {}", reserveServiceInfoDtoList);
        model.addAttribute("reserveInfoList", reserveInfoList);
        model.addAttribute("reserveServiceInfoDtoList", reserveServiceInfoDtoList);
        return "funeral/funeral_reserve_payment";
    }

    @GetMapping("funeral/funeral_reserve_confirm")
    public String reserveConfirm() {

        return "funeral/funeral_reserve_confirm";
    }


}
