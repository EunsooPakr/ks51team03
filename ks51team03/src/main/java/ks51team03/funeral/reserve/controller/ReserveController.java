package ks51team03.funeral.reserve.controller;

import jakarta.servlet.http.HttpSession;
import ks51team03.funeral.reserve.dto.ReserveDto;
import ks51team03.funeral.reserve.dto.ReserveInfoDto;
import ks51team03.funeral.reserve.service.ReserveService;
import ks51team03.funeral.serviceList.service.ServiceListService;
import ks51team03.member.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReserveController {

    private final ServiceListService serviceListService;
    private final ReserveService reserveService;
    private final MemberServiceImpl memberService;

    @GetMapping("funeral/funeral_reserve_info")
    public String reserveInfo(Model model, HttpSession session, ReserveInfoDto reserveInfoDto) {
        String memberId = (String) session.getAttribute("SID");

        log.info("장례 예약한 회원 아이디 memberId = {}", memberId);

        return "funeral/funeral_reserve_info";
    }

    @GetMapping("funeral/funeral_reserve_payment")
    public String reservePayment() {

        return "funeral/funeral_reserve_payment";
    }

    @GetMapping("funeral/funeral_reserve_confirm")
    public String reserveConfirm() {

        return "funeral/funeral_reserve_confirm";
    }


}
